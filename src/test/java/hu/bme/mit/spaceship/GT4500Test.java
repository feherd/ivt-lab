package hu.bme.mit.spaceship;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class GT4500Test {

  private GT4500 ship;
  private TorpedoStore torpedoStore1;
  private TorpedoStore torpedoStore2;

  @BeforeEach
  public void init() {
    torpedoStore1 = mock(TorpedoStore.class);
    torpedoStore2 = mock(TorpedoStore.class);
    this.ship = new GT4500(torpedoStore1, torpedoStore2);
  }

  @Test
  public void fireTorpedo_Single_Success() {
    // Arrange
    when(torpedoStore1.isEmpty()).thenReturn(false);
    when(torpedoStore1.getTorpedoCount()).thenReturn(1);
    when(torpedoStore1.fire(1)).thenReturn(true);

    // Act
    boolean result = ship.fireTorpedo(FiringMode.SINGLE);

    // Assert
    assertEquals(true, result);
    verify(torpedoStore1).fire(1);
  }

  @Test
  public void fireTorpedo_All_Success() {
    // Arrange
    when(torpedoStore1.isEmpty()).thenReturn(false);
    when(torpedoStore1.getTorpedoCount()).thenReturn(1);
    when(torpedoStore1.fire(1)).thenReturn(true);

    // Act
    boolean result = ship.fireTorpedo(FiringMode.ALL);

    // Assert
    assertEquals(true, result);
    verify(torpedoStore1).fire(1);
  }

  @Test
  public void fireTorpedo_Single_Twice() {
    // Arrange
    when(torpedoStore1.isEmpty()).thenReturn(false);
    when(torpedoStore1.getTorpedoCount()).thenReturn(1);
    when(torpedoStore1.fire(1)).thenReturn(true);
    when(torpedoStore2.isEmpty()).thenReturn(false);
    when(torpedoStore2.getTorpedoCount()).thenReturn(1);
    when(torpedoStore2.fire(1)).thenReturn(true);

    // Act
    ship.fireTorpedo(FiringMode.SINGLE);
    boolean result = ship.fireTorpedo(FiringMode.SINGLE);

    // Assert
    assertEquals(true, result);
    verify(torpedoStore2).fire(1);
  }

  @Test
  public void fireTorpedo_Single_Primary_Empty() {
    // Arrange
    when(torpedoStore1.isEmpty()).thenReturn(true);
    when(torpedoStore1.getTorpedoCount()).thenReturn(0);
    when(torpedoStore1.fire(1)).thenReturn(false);
    when(torpedoStore2.isEmpty()).thenReturn(false);
    when(torpedoStore2.getTorpedoCount()).thenReturn(1);
    when(torpedoStore2.fire(1)).thenReturn(true);

    // Act
    boolean result = ship.fireTorpedo(FiringMode.SINGLE);

    // Assert
    assertEquals(true, result);
    verify(torpedoStore1, never()).fire(1);
  }

  @Test
  public void fireTorpedo_Single_Primary_failing() {
    // Arrange
    when(torpedoStore1.isEmpty()).thenReturn(false);
    when(torpedoStore1.getTorpedoCount()).thenReturn(1);
    when(torpedoStore1.fire(1)).thenReturn(false);

    // Act
    boolean result = ship.fireTorpedo(FiringMode.SINGLE);

    // Assert
    assertEquals(false, result);
    verify(torpedoStore1).fire(1);
  }

  @Test
  public void fireTorpedo_All_Primary_failing() {
    // Arrange
    when(torpedoStore1.isEmpty()).thenReturn(false);
    when(torpedoStore1.getTorpedoCount()).thenReturn(1);
    when(torpedoStore1.fire(1)).thenReturn(false);
    when(torpedoStore2.isEmpty()).thenReturn(false);
    when(torpedoStore2.getTorpedoCount()).thenReturn(1);
    when(torpedoStore2.fire(1)).thenReturn(true);

    // Act
    boolean result = ship.fireTorpedo(FiringMode.ALL);

    // Assert
    assertEquals(true, result);
    verify(torpedoStore1).fire(1);
    verify(torpedoStore2).fire(1);
  }

  @Test
  public void fireTorpedo_All_Both_failing() {
    // Arrange
    when(torpedoStore1.isEmpty()).thenReturn(false);
    when(torpedoStore1.getTorpedoCount()).thenReturn(1);
    when(torpedoStore1.fire(1)).thenReturn(false);
    when(torpedoStore2.isEmpty()).thenReturn(false);
    when(torpedoStore2.getTorpedoCount()).thenReturn(1);
    when(torpedoStore2.fire(1)).thenReturn(false);

    // Act
    boolean result = ship.fireTorpedo(FiringMode.ALL);

    // Assert
    assertEquals(false, result);
    verify(torpedoStore1).fire(1);
    verify(torpedoStore2).fire(1);
  }

  @Test
  public void fireTorpedo_Single_Primary_Twice() {
    // Arrange
    when(torpedoStore1.isEmpty()).thenReturn(false);
    when(torpedoStore1.getTorpedoCount()).thenReturn(2);
    when(torpedoStore1.fire(1)).thenReturn(true);
    when(torpedoStore2.isEmpty()).thenReturn(true);
    when(torpedoStore2.getTorpedoCount()).thenReturn(0);
    when(torpedoStore2.fire(1)).thenReturn(true);

    // Act
    ship.fireTorpedo(FiringMode.SINGLE);
    boolean result = ship.fireTorpedo(FiringMode.SINGLE);

    // Assert
    assertEquals(true, result);
    verify(torpedoStore1, times(2)).fire(1);
    verify(torpedoStore2, never()).fire(1);
  }

  @Test
  public void fireTorpedo_Single_Both_Empty() {
    // Arrange
    when(torpedoStore1.isEmpty()).thenReturn(true);
    when(torpedoStore2.isEmpty()).thenReturn(true);

    // Act
    boolean result = ship.fireTorpedo(FiringMode.SINGLE);

    // Assert
    assertEquals(false, result);
    verify(torpedoStore1, never()).fire(1);
    verify(torpedoStore2, never()).fire(1);
  }

  @Test
  public void fireTorpedo_Single_Both_Empty_Primary_Fired_Last() {
    // Arrange
    when(torpedoStore1.isEmpty()).thenReturn(false);
    when(torpedoStore1.getTorpedoCount()).thenReturn(1);
    when(torpedoStore1.fire(1)).thenReturn(true);
    when(torpedoStore2.isEmpty()).thenReturn(true);

    ship.fireTorpedo(FiringMode.SINGLE);
    when(torpedoStore1.isEmpty()).thenReturn(true);

    // Act
    boolean result = ship.fireTorpedo(FiringMode.SINGLE);

    // Assert
    assertEquals(false, result);
    verify(torpedoStore1, times(1)).fire(1);
    verify(torpedoStore2, never()).fire(1);
  }

  @Test
  public void fireTorpedo_All_Both_Empty() {
    // Arrange
    when(torpedoStore1.isEmpty()).thenReturn(true);
    when(torpedoStore2.isEmpty()).thenReturn(true);

    // Act
    boolean result = ship.fireTorpedo(FiringMode.ALL);

    // Assert
    assertEquals(false, result);
    verify(torpedoStore1, never()).fire(1);
    verify(torpedoStore2, never()).fire(1);
  }
}
