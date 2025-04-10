package client.controller;

import static org.junit.Assert.*;

import org.junit.Test;

public class Controller2Test {

	final Controller controller = new Controller();


	  @Test
	  public void testCheckDettagliComposizione_CE1_CE3_CE6_CE8(){
	    boolean valA=true;
	    boolean valB=true;
	    boolean valC=false;
	    boolean valD=false;

	    String result=controller.checkDettagliComposizione(valA,valB,valC,valD);
	    assertEquals(" AND ascensore = 'TRUE' AND condominio = 'TRUE'", result);

	  }

	  @Test
	  public void testCheckDettagliComposizione_CE2_CE4_CE5_CE7(){
	    boolean valA=false;
	    boolean valB=false;
	    boolean valC=true;
	    boolean valD=true;

	    String result=controller.checkDettagliComposizione(valA,valB,valC,valD);
	    assertEquals(" AND terrazzo = 'TRUE' AND giardino = 'TRUE'", result);

	  }

	  @Test
	  public void testCheckDettagliComposizione_CE1_CE3_CE5_CE7(){
	    boolean valA=true;
	    boolean valB=true;
	    boolean valC=true;
	    boolean valD=true;

	    String result=controller.checkDettagliComposizione(valA,valB,valC,valD);
	    assertEquals(" AND ascensore = 'TRUE' AND condominio = 'TRUE' AND terrazzo = 'TRUE' AND giardino = 'TRUE'", result);

	  }

	  @Test
	  public void testCheckDettagliComposizione_CE2_CE4_CE6_CE8(){
	    boolean valA=false;
	    boolean valB=false;
	    boolean valC=false;
	    boolean valD=false;

	    String result=controller.checkDettagliComposizione(valA,valB,valC,valD);
	    assertEquals("", result);

	  }

}
