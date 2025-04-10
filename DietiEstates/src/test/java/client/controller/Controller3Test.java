package client.controller;

import static org.junit.Assert.*;

import org.junit.Test;

public class Controller3Test {

	final Controller controller = new Controller();
	  
	  @Test
	  public void testCheckDettagliImmobile_CE1_CE10_CE14(){
	    String classe = "";
	    String tipo = "Casa";
	    String annuncio = "Vendita";
	    
	    String result = controller.checkDettagliImmobile(classe, tipo, annuncio);
	    assertEquals(" AND tipo = 'Casa' AND annuncio = 'Vendita'", result);
	  }
	  
	  @Test
	  public void testCheckDettagliImmobile_CE2_CE9_CE15(){
	    String classe = "A";
	    String tipo = "";
	    String annuncio = "Affitto";
	    
	    String result = controller.checkDettagliImmobile(classe, tipo, annuncio);
	    assertEquals(" AND classe_energetica = 'A' AND annuncio = 'Affitto'", result);
	  } 
	  
	  @Test
	  public void testCheckDettagliImmobile_CE3_CE11_CE13(){
	    String classe = "B";
	    String tipo = "Appartamento";
	    String annuncio = "";
	    
	    String result = controller.checkDettagliImmobile(classe, tipo, annuncio);
	    assertEquals(" AND classe_energetica = 'B' AND tipo = 'Appartamento'", result);
	  } 
	  
	  @Test
	  public void testCheckDettagliImmobile_CE4_CE12_CE13(){
	    String classe = "C";
	    String tipo = "Villa";
	    String annuncio = "";
	    
	    String result = controller.checkDettagliImmobile(classe, tipo, annuncio);
	    assertEquals(" AND classe_energetica = 'C' AND tipo = 'Villa'", result);
	  } 
	  
	  @Test
	  public void testCheckDettagliImmobile_CE5_CE11_CE14(){
	    String classe = "D";
	    String tipo = "Appartamento";
	    String annuncio = "Vendita";
	    
	    String result = controller.checkDettagliImmobile(classe, tipo, annuncio);
	    assertEquals(" AND classe_energetica = 'D' AND tipo = 'Appartamento' AND annuncio = 'Vendita'", result);
	  } 
	  
	  @Test
	  public void testCheckDettagliImmobile_CE6_CE10_CE15(){
	    String classe = "E";
	    String tipo = "Casa";
	    String annuncio = "Affitto";
	    
	    String result = controller.checkDettagliImmobile(classe, tipo, annuncio);
	    assertEquals(" AND classe_energetica = 'E' AND tipo = 'Casa' AND annuncio = 'Affitto'", result);
	  } 
	  
	  @Test
	  public void testCheckDettagliImmobile_CE7_CE9_CE13(){
	    String classe = "F";
	    String tipo = "";
	    String annuncio = "";
	    
	    String result = controller.checkDettagliImmobile(classe, tipo, annuncio);
	    assertEquals(" AND classe_energetica = 'F'", result);
	  } 
	    
	  @Test
	  public void testCheckDettagliImmobile_CE8_CE11_CE14(){
	    String classe = "G";
	    String tipo = "Appartamento";
	    String annuncio = "Vendita";
	    
	    String result = controller.checkDettagliImmobile(classe, tipo, annuncio);
	    assertEquals(" AND classe_energetica = 'G' AND tipo = 'Appartamento' AND annuncio = 'Vendita'", result);
	  } 

}
