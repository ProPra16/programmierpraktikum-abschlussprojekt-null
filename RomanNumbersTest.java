import static org.junit.Assert.*;
import org.junit.*;

public class RomanNumbersTest {

  //Hier werden alle Randfälle uberprüft

  @Test
  public void EinsErgibtI() {
    String berechnet = RomanNumbers.roman(1);
    String erwartet = "I";
    assertEquals(erwartet, berechnet);
  }

  @Test
  public void ZweiErgibtII() {
    String berechnet = RomanNumbers.roman(2);
    String erwartet = "II";
    assertEquals(erwartet, berechnet);
  }

  @Test
  public void VierErgibtIV() {
    String berechnet = RomanNumbers.roman(4);
    String erwartet = "IV";
    assertEquals(erwartet, berechnet);
  }

  @Test
  public void SiebenErgibtVII() {
    String berechnet = RomanNumbers.roman(7);
    String erwartet = "VII";
    assertEquals(erwartet, berechnet);
  }

  @Test
  public void NeunErgibtIX() {
    String berechnet = RomanNumbers.roman(9);
    String erwartet = "IX";
    assertEquals(erwartet, berechnet);
  }

  @Test
  public void ZwanzigErgibtXX() {
    String berechnet = RomanNumbers.roman(20);
    String erwartet = "XX";
    assertEquals(erwartet, berechnet);
  }

  @Test
  public void NeunundzwanzigErgibtXIX() {
    String berechnet = RomanNumbers.roman(29);
    String erwartet = "XXIX";
    assertEquals(erwartet, berechnet);
  }

  @Test
  public void NeununddreißigErgibtXLIX() {
    String berechnet = RomanNumbers.roman(39);
    String erwartet = "XXXIX";
    assertEquals(erwartet, berechnet);
  }

  @Test
  public void dieZahlVierzigErgibtXL() {
    String berechnet = RomanNumbers.roman(40);
    String erwartet = "XL";
    assertEquals(erwartet, berechnet);
  }
}
