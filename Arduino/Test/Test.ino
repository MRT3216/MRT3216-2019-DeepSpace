// Simple NeoPixel test.  Lights just a few pixels at a time so a
// 1m strip can safely be powered from Arduino 5V pin.  Arduino
// may nonetheless hiccup when LEDs are first connected and not
// accept code.  So upload code first, unplug USB, connect pixels
// to GND FIRST, then +5V and digital pin 6, then re-plug USB.
// A working strip will show a few pixels moving down the line,
// cycling between red, green and blue.  If you get no response,
// might be connected to wrong end of strip (the end wires, if
// any, are no indication -- look instead for the data direction
// arrows printed on the strip).
 
#include <Adafruit_NeoPixel.h>
 
#define RING_PIN      6
#define UPRIGHT_PIN   1
#define REAR_PIN      2
#define SIDE_PIN      3
#define RING_LEDS 16
#define UPRIGHT_LEDS 12
#define REAR_LEDS 24
#define SIDE_LEDS 36
 
Adafruit_NeoPixel ring = Adafruit_NeoPixel(RING_LEDS, RING_PIN, NEO_GRB + NEO_KHZ800);
Adafruit_NeoPixel upright = Adafruit_NeoPixel(UPRIGHT_LEDS, UPRIGHT_PIN, NEO_GRB + NEO_KHZ800);
Adafruit_NeoPixel side = Adafruit_NeoPixel(SIDE_LEDS, SIDE_PIN, NEO_GRB + NEO_KHZ800);
Adafruit_NeoPixel rear = Adafruit_NeoPixel(REAR_LEDS, REAR_PIN, NEO_GRB + NEO_KHZ800);
 
void setup() {
  ring.begin();
  ring.show();
  upright.begin();
  upright.show();
  side.begin();
  side.show();
  rear.begin();
  rear.show();
}
 
void loop() {
  chase(ring, ring.Color(255, 0, 0)); // Red
  chase(ring, ring.Color(0, 255, 0)); // Green
  chase(ring, ring.Color(0, 0, 255)); // Blue
  chase(upright, upright.Color(255, 0, 0)); // Red
  chase(upright, upright.Color(0, 255, 0)); // Green
  chase(upright, upright.Color(0, 0, 255)); // Blue
  chase(side, side.Color(255, 0, 0)); // Red
  chase(side, side.Color(0, 255, 0)); // Green
  chase(side, side.Color(0, 0, 255)); // Blue
  chase(rear, rear.Color(255, 0, 0)); // Red
  chase(rear, rear.Color(0, 255, 0)); // Green
  chase(rear, rear.Color(0, 0, 255)); // Blue
}
 
static void chase(Adafruit_NeoPixel& strip, uint32_t c) {
  for(uint16_t i=0; i<strip.numPixels()+4; i++) {
      strip.setPixelColor(i  , c); // Draw new pixel
      strip.setPixelColor(i-4, 0); // Erase pixel a few steps back
      strip.show();
      delay(25);
  }
}