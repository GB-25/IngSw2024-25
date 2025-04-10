package client.controller;

import static org.junit.Assert.*;

import org.junit.Test;

public class Controller1Test {

	final Controller controller = new Controller();
	
	@Test
	  public void testCheckPrezzi_CE1_CE5(){
	    double prezzoA=90000;
	    double prezzoB=400;

	    boolean result = controller.checkPrezzi(prezzoA, prezzoB);
	    assertFalse(result);
	  }

	  @Test
	  public void testCheckPrezzi_CE1_CE6(){
	    double prezzoA=80000;
	    double prezzoB=400000000;

	    boolean result = controller.checkPrezzi(prezzoA, prezzoB);
	    assertFalse(result);
	  }

	  @Test
	  public void testCheckPrezzi_CE2_CE4(){
	      double prezzoA=100;
	      double prezzoB=40000;

	      boolean result = controller.checkPrezzi(prezzoA, prezzoB);
	      assertFalse(result);
	    }

	  @Test
	  public void testCheckPrezzi_CE3_CE4(){
	      double prezzoA=100000000;
	      double prezzoB=40000;

	      boolean result = controller.checkPrezzi(prezzoA, prezzoB);
	      assertFalse(result);
	    }

	  @Test
	  public void testCheckPrezzi_CE8(){
	      double prezzoA=100000;
	      double prezzoB=40000;

	      boolean result = controller.checkPrezzi(prezzoA, prezzoB);
	      assertFalse(result);
	    }

	  @Test
	  public void testCheckPrezzi_CE1_CE4_CE7(){
	      double prezzoA=1000;
	      double prezzoB=40000;

	      boolean result = controller.checkPrezzi(prezzoA, prezzoB);
	      assertTrue(result);
	    }
}
