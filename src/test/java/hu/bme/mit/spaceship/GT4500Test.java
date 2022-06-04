package hu.bme.mit.spaceship;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.*;

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
    when(torpedoStore1.getTorpedoCount()).thenReturn(1);
    when(torpedoStore1.fire(1)).thenReturn(true);

    // Act
    boolean result = ship.fireTorpedo(FiringMode.ALL);

    // Assert
    assertEquals(true, result);
    verify(torpedoStore1).fire(1);
  }

}
