package client.controller;

import static org.junit.Assert.*;

import org.junit.Test;

public class Controller4Test {

	final Controller controller = new Controller();

	  @Test
	  public void testCheckDettagliInserzione_CE1_CE6_CE9(){
	    String quadratura = "trecento";
	    String piani = "3";
	    String stanze = "5";
	    
	    boolean result = controller.checkDettagliInserzione(quadratura, piani, stanze);
	    assertFalse(result);
	  }
	  
	  @Test
	  public void testCheckDettagliInserzione_CE2_CE6_CE9(){
	    String quadratura = "0";
	    String piani = "1";
	    String stanze = "6";
	    
	    boolean result = controller.checkDettagliInserzione(quadratura, piani, stanze);
	    assertFalse(result);
	  }
	  
	  @Test
	  public void testCheckDettagliInserzione_CE3_CE4_CE9(){
	    String quadratura = "500";
	    String piani = "quattro";
	    String stanze = "3";
	    
	    boolean result = controller.checkDettagliInserzione(quadratura, piani, stanze);
	    assertFalse(result);
	  }
	  
	  @Test
	  public void testCheckDettagliInserzione_CE3_CE5_CE9(){
	    String quadratura = "128";
	    String piani = "0";
	    String stanze = "6";
	    
	    boolean result = controller.checkDettagliInserzione(quadratura, piani, stanze);
	    assertFalse(result);
	  }
	  
	  @Test
	  public void testCheckDettagliInserzione_CE3_CE6_CE7(){
	    String quadratura = "234";
	    String piani = "2";
	    String stanze = "sette";
	    
	    boolean result = controller.checkDettagliInserzione(quadratura, piani, stanze);
	    assertFalse(result);
	  }
	  
	  @Test
	  public void testCheckDettagliInserzione_CE3_CE6_CE8(){
	    String quadratura = "143";
	    String piani = "3";
	    String stanze = "0";
	    
	    boolean result = controller.checkDettagliInserzione(quadratura, piani, stanze);
	    assertFalse(result);
	  }
	  
	  @Test
	  public void testCheckDettagliInserzione_CE3_CE6_CE9(){
	    String quadratura = "89";
	    String piani = "1";
	     String stanze = "3";
	    
	    boolean result = controller.checkDettagliInserzione(quadratura, piani, stanze);
	    assertTrue(result);
	  }

}
