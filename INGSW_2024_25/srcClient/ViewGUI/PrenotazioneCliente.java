package ViewGUI;

import java.awt.*;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import javax.swing.*;
import com.toedter.calendar.JDateChooser;

import Class.Immobile;
import Class.User;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.json.JSONArray;
import org.json.JSONObject;
import Controller.Controller;

public class PrenotazioneCliente extends JFrame {

    private JDateChooser dateChooser;
    private JPanel mainPanel;

    public PrenotazioneCliente(Controller c, Immobile immobile, User user) {
        setTitle("Prenotazione Cliente - DietiEstates25");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 400);
        setLocationRelativeTo(null);
        mainPanel = new JPanel(new BorderLayout());
        setContentPane(mainPanel);

        JPanel middlePanel = new JPanel(new GridBagLayout());
        middlePanel.setPreferredSize(new Dimension(600, 180));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.anchor = GridBagConstraints.WEST;

        // Panel per il tasto indietro ed il titolo
        JPanel indietroPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JButton indietroButton = new JButton("←");
        indietroButton.setPreferredSize(new Dimension(60, 25));
        indietroButton.setFont(new Font("Arial", Font.PLAIN, 12));
        indietroButton.addActionListener(e -> dispose());
        indietroPanel.add(indietroButton);

        JLabel phraseLabel = new JLabel("Specifica la data e l'orario di prenotazione.");
        phraseLabel.setFont(new Font("Helvetica", Font.BOLD, 20));
        phraseLabel.setForeground(Color.BLACK);
        indietroPanel.add(phraseLabel);
        mainPanel.add(indietroPanel, BorderLayout.NORTH);

        // Selezione della data
        dateChooser = new JDateChooser();
        dateChooser.setDateFormatString("yyyy-MM-dd");
        ((JTextField) dateChooser.getDateEditor().getUiComponent()).setEditable(false);
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, 14);
        Date dataMassima = cal.getTime();
        cal = Calendar.getInstance();
        cal.add(Calendar.DATE, 0);
        Date dataMinima = cal.getTime();
        dateChooser.setSelectableDateRange(dataMinima, dataMassima);
        dateChooser.setPreferredSize(new Dimension(176, 25));

        JLabel dateLabel = new JLabel("Data:");
        dateLabel.setPreferredSize(new Dimension(150, 25));

        gbc.gridx = 0; gbc.gridy = 0;
        middlePanel.add(dateLabel, gbc);
        gbc.gridx = 1; gbc.gridy = 0;
        middlePanel.add(dateChooser, gbc);

        // Selezione dell'orario
        SpinnerDateModel model = new SpinnerDateModel();
        JSpinner timeSpinner = new JSpinner(model);
        JSpinner.DateEditor editor = new JSpinner.DateEditor(timeSpinner, "HH:mm");
        timeSpinner.setEditor(editor);
        timeSpinner.setPreferredSize(new Dimension(176, 25));

        JLabel timeLabel = new JLabel("Orario:");
        timeLabel.setPreferredSize(new Dimension(150, 25));

        gbc.gridx = 0; gbc.gridy = 1;
        middlePanel.add(timeLabel, gbc);
        gbc.gridx = 1; gbc.gridy = 1;
        middlePanel.add(timeSpinner, gbc);

        JButton confirmButton = new JButton("Conferma Orario");
        gbc.gridx = 0; gbc.gridy = 2;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        middlePanel.add(confirmButton, gbc);

        JLabel outputLabel = new JLabel(" ");
        gbc.gridy = 3;
        middlePanel.add(outputLabel, gbc);

        JLabel weatherLabel = new JLabel(" ");
        gbc.gridy = 4;
        middlePanel.add(weatherLabel, gbc);
        
        JButton weatherButton = new JButton("Controlla meteo");
        gbc.gridy = 5;
        middlePanel.add(weatherButton, gbc);
        
        // Tasto per sapere il meteo della data inserita nell'orario inserito
        weatherButton.addActionListener(e -> {
        	Date selectedTime = (Date) timeSpinner.getValue();
            Date selectedDate = dateChooser.getDate();

            Calendar calendar = Calendar.getInstance();
            calendar.setTime(selectedTime);

            int hour = calendar.get(Calendar.HOUR_OF_DAY);
            int minute = calendar.get(Calendar.MINUTE);
            if (selectedDate == null || !( hour >= 10 && hour < 18 && minute == 00 || hour >= 10 && hour < 18 && minute == 30 || hour == 18 && minute == 0)) {
                outputLabel.setText("⚠️ Seleziona una data ed un orario ad intervalli di mezz'ora (10:00 - 18:00).");
                return;
            }
            
         // Ottieni meteo
            String formattedDate = new SimpleDateFormat("yyyy-MM-dd").format(selectedDate);
            new Thread(() -> {
                String weatherInfo = getWeather(45.4642, 9.1900, formattedDate, hour); // latitudine e longitudine placeholder
                SwingUtilities.invokeLater(() -> weatherLabel.setText(weatherInfo));
            }).start();
        });

        // Tasto per confermare la prenotazione. Da aggiustare ovviamente la conferma e tutto il resto, ma per il momento la base è questa
        confirmButton.addActionListener(e -> {
            Date selectedTime = (Date) timeSpinner.getValue();
            Date selectedDate = dateChooser.getDate();
            if (selectedDate == null) {
                outputLabel.setText("⚠️ Seleziona una data ed un orario ad intervalli di mezz'ora (10:00 - 18:00).");
                return;
            }

            Calendar calendar = Calendar.getInstance();
            calendar.setTime(selectedTime);

            int hour = calendar.get(Calendar.HOUR_OF_DAY);
            int minute = calendar.get(Calendar.MINUTE);

            if (hour >= 10 && hour < 18 && minute == 00 || hour >= 10 && hour < 18 && minute == 30 || (hour == 18 && minute == 0)) {
            	String orario = new String(Integer.toString(hour)+":"+Integer.toString(minute)); // QUESTA E' DA PASSARE, ORARIO A STRINGA
                outputLabel.setText("✅ Orario confermato: " + new SimpleDateFormat("HH:mm").format(selectedTime));
            } else {
                outputLabel.setText("⚠️ Orario fuori range! (10:00 - 18:00)");
            }
        });

        mainPanel.add(middlePanel, BorderLayout.CENTER);
    }

    private String getWeather(double latitude, double longitude, String date, int hour) {
        try {
            // Costruzione URL per ottenere il meteo per l'intera giornata
            String urlString = String.format(
                "https://api.open-meteo.com/v1/forecast?latitude=%.2f&longitude=%.2f&hourly=temperature_2m,weathercode&start_date=%s&end_date=%s&timezone=auto",
                latitude, longitude, date, date
            );

            System.out.println("URL della richiesta: " + urlString); // Usato per debug, stampa l'URL

            HttpURLConnection conn = (HttpURLConnection) new URL(urlString).openConnection();
            conn.setRequestMethod("GET");

            // Verifica il codice di risposta HTTP (fatto solo perché stavo impazzendo con l'API e non riuscivo a capire il problema)
            int responseCode = conn.getResponseCode();
            if (responseCode != 200) {
                return "Errore HTTP: " + responseCode;
            }

            BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            StringBuilder response = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                response.append(line);
            }
            reader.close();

            // Stampa la risposta API (fatto solo perché stavo impazzendo con l'API e non riuscivo a capire il problema)
            System.out.println("Risposta API: " + response.toString());

            // JSONArray perché la risposta URL arriva come JSONArray e non come JSONObject
            JSONArray jsonArray = new JSONArray(response.toString());

            // Se la risposta è un array, prendi il primo elemento (ovvero un JSONObject)
            if (jsonArray.length() == 0) {
                return "⚠️ Nessun dato meteo disponibile!";
            }

            JSONObject jsonResponse = jsonArray.getJSONObject(0); // Prendi il primo oggetto nell'array

            // Controlla se il JSON contiene i dati richiesti
            if (!jsonResponse.has("hourly")) {
                return "⚠️ Dati meteo non disponibili!";
            }

            JSONObject hourlyData = jsonResponse.getJSONObject("hourly");

            if (!hourlyData.has("temperature_2m") || !hourlyData.has("weathercode")) {
                return "⚠️ Dati meteo incompleti!";
            }

            // Estrai la temperatura e il meteo per l'ora richiesta
            int index = hour; // L'ora è l'indice dell'array hourly
            double temperature = hourlyData.getJSONArray("temperature_2m").getDouble(index);
            int weatherCode = hourlyData.getJSONArray("weathercode").getInt(index);

            return String.format("🌡️ %.1f°C | ☁️ %s", temperature, getWeatherDescription(weatherCode)); // va là che belline le iconcine

        } catch (Exception e) {
            e.printStackTrace();
            return "❌ Errore nel recupero meteo: " + e.getMessage();
        }
    }



    private String getWeatherDescription(int code) {
        return switch (code) {
            case 0 -> "Soleggiato";
            case 1, 2, 3 -> "Parzialmente nuvoloso";
            case 45, 48 -> "Nebbia";
            case 51, 53, 55 -> "Pioviggine";
            case 61, 63, 65 -> "Pioggia";
            case 80, 81, 82 -> "Rovesci";
            case 95, 96, 99 -> "Temporale";
            default -> "Sconosciuto";
        };
    }
}

