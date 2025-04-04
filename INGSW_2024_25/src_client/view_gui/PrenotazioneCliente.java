package view_gui;

import java.awt.*;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URISyntaxException;

import javax.swing.*;

import com.formdev.flatlaf.FlatLaf;
import com.formdev.flatlaf.FlatLightLaf;
import com.toedter.calendar.JDateChooser;

import classi.Immobile;
import classi.User;
import controller.Controller;
import eccezioni.GeocodingException;
import java.util.logging.Logger;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.json.JSONArray;
import org.json.JSONObject;

public class PrenotazioneCliente extends JFrame {
	private static final long serialVersionUID = 1L;
	private JDateChooser dateChooser;
    private JPanel mainPanel;
    private String orario;
    private JFrame finestraCorrente;
    private SchermataCaricamento schermataCaricamento;
    private transient Logger logger = Logger.getLogger(getClass().getName());
    private static final String SELEZIONA = "Seleziona una data ed un orario ad intervalli di mezz'ora (10:00 - 18:00).";
    /**
     * 
     * Costruttore
     */
    public PrenotazioneCliente(Controller c, Immobile immobile, User user, JFrame finestraPrecedente) {
    	FlatLaf.setup(new FlatLightLaf());
    	finestraCorrente = this;
        setTitle("Prenotazione Cliente - DietiEstates25");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(650, 400);
        setLocationRelativeTo(null);
        mainPanel = new JPanel(new BorderLayout());
        setContentPane(mainPanel);
        setResizable(false);
        JPanel middlePanel = new JPanel();
        middlePanel.setPreferredSize(new Dimension(600, 180));

        JPanel indietroPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        indietroPanel.setBackground(new Color(40, 132, 212));

        JLabel phraseLabel = new JLabel("                        Specifica la data e l'orario di prenotazione.");
        phraseLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        phraseLabel.setFont(new Font("Microsoft YaHei UI Light", Font.BOLD, 18));
        phraseLabel.setForeground(new Color(255, 255, 255));
        indietroPanel.add(phraseLabel);
        mainPanel.add(indietroPanel, BorderLayout.NORTH);

        dateChooser = new JDateChooser();
        dateChooser.setBounds(307, 54, 176, 25);
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
        middlePanel.setLayout(null);

        JLabel dateLabel = new JLabel("Data:");
        dateLabel.setBounds(137, 54, 150, 25);
        dateLabel.setPreferredSize(new Dimension(150, 25));
        middlePanel.add(dateLabel);
        middlePanel.add(dateChooser);

        SpinnerDateModel model = new SpinnerDateModel();
        JSpinner timeSpinner = new JSpinner(model);
        timeSpinner.setBounds(307, 99, 176, 25);
        JSpinner.DateEditor editor = new JSpinner.DateEditor(timeSpinner, "HH:mm");
        timeSpinner.setEditor(editor);
        timeSpinner.setPreferredSize(new Dimension(176, 25));

        JLabel timeLabel = new JLabel("Orario:");
        timeLabel.setBounds(137, 99, 150, 25);
        timeLabel.setPreferredSize(new Dimension(150, 25));
        middlePanel.add(timeLabel);
        middlePanel.add(timeSpinner);

        JButton confirmButton = new JButton("Prenota");
        confirmButton.setBounds(262, 144, 105, 21);
        middlePanel.add(confirmButton);

        JLabel outputLabel = new JLabel(SELEZIONA);
        outputLabel.setBounds(62, 186, 509, 13);
        middlePanel.add(outputLabel);

        JLabel weatherLabel = new JLabel(" ");
        weatherLabel.setHorizontalAlignment(SwingConstants.CENTER);
        weatherLabel.setBounds(175, 224, 264, 13);
        middlePanel.add(weatherLabel);
        
        JButton weatherButton = new JButton("<html><center>Controlla<br>Meteo</center></html>");
        weatherButton.setBounds(262, 259, 105, 31);
        middlePanel.add(weatherButton);

        weatherButton.addActionListener(e -> getWeather (outputLabel, timeSpinner, weatherLabel));

        confirmButton.addActionListener(e -> postReservation(c, timeSpinner, outputLabel, user, immobile));

        mainPanel.add(middlePanel, BorderLayout.CENTER);
        JButton indietroButton = new JButton("←");
        indietroButton.setBounds(21, 10, 60, 25);
        middlePanel.add(indietroButton);
        indietroButton.setPreferredSize(new Dimension(60, 25));
        indietroButton.setFont(new Font("Arial", Font.PLAIN, 12));
        indietroButton.addActionListener(e -> {
        	schermataCaricamento = c.createSchermataCaricamento(finestraCorrente, "Caricamento");
   		 SwingWorker<Void, Void> worker = new SwingWorker<Void, Void>() {			 
   			 @Override
   			 protected Void doInBackground () throws Exception {
        	dispose();
        	try {
        		if (finestraPrecedente instanceof VisioneImmobile) {
        			c.showImmobile(finestraCorrente, immobile, user, null);
        		}
        		else {
        			c.createHomeUtente(finestraCorrente, user)  ;
        			}
			} catch (GeocodingException | URISyntaxException e1) {
				logger.severe("Errore nel caricamento della finestra");
			} return null;}
   			 @Override
   			 protected void done() {
   				 schermataCaricamento.close();}};
   				 worker.execute();});
    }
    /**
     * Metodo per ottenere i dati meteo
     * @param latitude
     * @param longitude
     * @param date
     * @param hour
     * @return String che rappresenta meteo e temperatura
     */
    private String getWeather(double latitude, double longitude, String date, int hour) {
        try {
          
            String urlString = String.format(
                "https://api.open-meteo.com/v1/forecast?latitude=%.2f&longitude=%.2f&hourly=temperature_2m,weathercode&start_date=%s&end_date=%s&timezone=auto",
                latitude, longitude, date, date
            );

            URI uri = new URI(urlString);
            HttpURLConnection conn = (HttpURLConnection) uri.toURL().openConnection();
            conn.setRequestMethod("GET");

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

            JSONArray jsonArray = new JSONArray(response.toString());

            if (jsonArray.length() == 0) {
                return "⚠️ Nessun dato meteo disponibile!";
            }

            JSONObject jsonResponse = jsonArray.getJSONObject(0); 

            if (!jsonResponse.has("hourly")) {
                return "⚠️ Dati meteo non disponibili!";
            }

            JSONObject hourlyData = jsonResponse.getJSONObject("hourly");

            if (!hourlyData.has("temperature_2m") || !hourlyData.has("weathercode")) {
                return "⚠️ Dati meteo incompleti!";
            }

            int index = hour;
            double temperature = hourlyData.getJSONArray("temperature_2m").getDouble(index);
            int weatherCode = hourlyData.getJSONArray("weathercode").getInt(index);

            return String.format("🌡️ %.1f°C | ☁️ %s", temperature, getWeatherDescription(weatherCode)); // va là che belline le iconcine

        } catch (Exception e) {
        	logger.severe("Errore comunicazine API meteo");
            return "❌ Errore nel recupero meteo: " + e.getMessage();
        }
    }
    /**
     * Creazione prenotazione
     * @param c
     * @param timeSpinner
     * @param outputLabel
     * @param user
     * @param immobile
     */
    private void postReservation(Controller c, JSpinner timeSpinner, JLabel outputLabel, User user, Immobile immobile) {
    	schermataCaricamento = c.createSchermataCaricamento(finestraCorrente, "Caricamento");
		 SwingWorker<Void, Void> worker = new SwingWorker<Void, Void>() {			 
			 @Override
			 protected Void doInBackground () throws Exception {
        Date selectedTime = (Date) timeSpinner.getValue();
        Date selectedDate = dateChooser.getDate();
        if (selectedDate == null ) {
            outputLabel.setText("⚠️ " + SELEZIONA);
            return null;
        }
        

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(selectedTime);

        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);

        if (hour >= 10 && hour < 18 && minute == 00 || hour >= 10 && hour < 18 && minute == 30 || (hour == 18 && minute == 0)) {
        	if (minute == 0) {
        	    orario = (Integer.toString(hour)+":"+Integer.toString(minute)+"0");} // QUESTA E' DA PASSARE, ORARIO A STRINGA
        	else {
        		orario = (Integer.toString(hour)+":"+Integer.toString(minute));}
        	String data = ((JTextField) dateChooser.getDateEditor().getUiComponent()).getText();
            c.createReservation(user, immobile, data, orario, finestraCorrente);
            c.reservationConfirmed(finestraCorrente, user);
        } else {
            outputLabel.setText("⚠️ " + SELEZIONA);
        } return null;}
			 
			 @Override
			 protected void done() {
				 schermataCaricamento.close();
			 }}; worker.execute();
    }

    private void getWeather (JLabel outputLabel, JSpinner timeSpinner, JLabel weatherLabel) {
    	Date selectedTime = (Date) timeSpinner.getValue();
        Date selectedDate = dateChooser.getDate();

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(selectedTime);

        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);
        if (selectedDate == null || !( hour >= 10 && hour < 18 && minute == 00 || hour >= 10 && hour < 18 && minute == 30 || hour == 18 && minute == 0)) {
            outputLabel.setText("⚠️ " + SELEZIONA);
            return;
            }
 
        String formattedDate = new SimpleDateFormat("yyyy-MM-dd").format(selectedDate);
        new Thread(() -> {
            String weatherInfo = getWeather(45.4642, 9.1900, formattedDate, hour);
            SwingUtilities.invokeLater(() -> weatherLabel.setText(weatherInfo));
        }).start();
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

