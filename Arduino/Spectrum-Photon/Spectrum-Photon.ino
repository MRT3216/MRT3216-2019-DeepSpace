
#include <CmdMessenger.h>  // CmdMessenger
#include<FastLED.h>
#include "LEDstrip.cpp"

/*
 * Red (0..) "HUE_RED"
Orange (32..) "HUE_ORANGE"
Yellow (64..) "HUE_YELLOW"
Green (96..) "HUE_GREEN"
Aqua (128..) "HUE_AQUA"
Blue (160..) "HUE_BLUE"
Purple (192..) "HUE_PURPLE"
Pink(224..) "HUE_PINK"

// Pin layouts on the teensy 3:
// OctoWS2811: 2,14,7,8,6,20,21,5

2  LED Strip #1
14  LED Strip #2
7 LED Strip #3
8 LED Strip #4
6 LED Strip #5
20  LED Strip #6
21  LED Strip #7
5 LED Strip #8
*/

#define LED_UPDATE_PERIOD 10
#define MAX_LEDS_PER_STRIP 60

#define STRIP_1_PIN 2
#define STRIP_2_PIN 14
#define STRIP_3_PIN 7
#define STRIP_4_PIN 8
#define STRIP_5_PIN 6
#define STRIP_6_PIN 20
#define STRIP_7_PIN 21
#define STRIP_8_PIN 5
 
CRGB leds1[MAX_LEDS_PER_STRIP];
CRGB leds2[MAX_LEDS_PER_STRIP];
CRGB leds3[MAX_LEDS_PER_STRIP];
CRGB leds4[MAX_LEDS_PER_STRIP];
CRGB leds5[MAX_LEDS_PER_STRIP];
CRGB leds6[MAX_LEDS_PER_STRIP];
CRGB leds7[MAX_LEDS_PER_STRIP];
CRGB leds8[MAX_LEDS_PER_STRIP];

LEDstrip strip1(MAX_LEDS_PER_STRIP);
LEDstrip strip2(MAX_LEDS_PER_STRIP);
LEDstrip strip3(MAX_LEDS_PER_STRIP);
LEDstrip strip4(MAX_LEDS_PER_STRIP);
LEDstrip strip5(MAX_LEDS_PER_STRIP);
LEDstrip strip6(MAX_LEDS_PER_STRIP);
LEDstrip strip7(MAX_LEDS_PER_STRIP);
LEDstrip strip8(MAX_LEDS_PER_STRIP);

// Attach a new CmdMessenger object to the default Serial port
CmdMessenger cmdMessenger = CmdMessenger(Serial);

// This is the list of recognized commands. These can be commands that can either be sent or received. 
// In order to receive, attach a callback function to these events
enum
{
  // Commands
  kAcknowledge         , // 0 Command to acknowledge that cmd was received
  kError               , // 1 Command to report errors
  kSetStrip            , // 2 Command to request add two floats
  kSetNumLEDSstrip     , // 3 Command to report addition result
};

///////////////////////////////////////////////////////////////////////////
/// REST OF THE COMMANDS ARE DEFINED IN THE commands TAB //////////////////
///////////////////////////////////////////////////////////////////////////

//Variables for cycling the LEDs
unsigned long time;
unsigned long oldTime;
unsigned long counter = 0;

//Runs once at startup
void setup() {
  //Setup your LED strips here, up to 8 can be used, maybe more.
  LEDS.addLeds<WS2812,STRIP_1_PIN,GRB>(leds1,MAX_LEDS_PER_STRIP);
  LEDS.addLeds<WS2812,STRIP_2_PIN,GRB>(leds2,MAX_LEDS_PER_STRIP);
  LEDS.addLeds<WS2812,STRIP_3_PIN,GRB>(leds3,MAX_LEDS_PER_STRIP);
  LEDS.addLeds<WS2812,STRIP_4_PIN,GRB>(leds4,MAX_LEDS_PER_STRIP);
  LEDS.addLeds<WS2812,STRIP_5_PIN,GRB>(leds5,MAX_LEDS_PER_STRIP);
  LEDS.addLeds<WS2812,STRIP_6_PIN,GRB>(leds6,MAX_LEDS_PER_STRIP);
  LEDS.addLeds<WS2812,STRIP_7_PIN,GRB>(leds7,MAX_LEDS_PER_STRIP);
  LEDS.addLeds<WS2812,STRIP_8_PIN,GRB>(leds8,MAX_LEDS_PER_STRIP);
  LEDS.setBrightness(255);

  Serial.begin(115200);
  // Adds newline to every command
  cmdMessenger.printLfCr();  
  
  // Attach my application's user-defined callback methods
  attachCommandCallbacks();

  // Send the status to the PC that says the Arduino has booted
  cmdMessenger.sendCmd(kAcknowledge,"Arduino has started!");

  time = millis();
  oldTime = time;
}

void loop() {
  // Process incoming serial data, and perform callbacks
  cmdMessenger.feedinSerialData();
  
  time = millis();
  if(time - oldTime > LED_UPDATE_PERIOD){ 
    oldTime = time;
    strip1.Update(counter, leds1);
    strip2.Update(counter, leds2);
    strip3.Update(counter, leds3);
    strip4.Update(counter, leds4);
    strip5.Update(counter, leds5);
    strip6.Update(counter, leds6);
    strip7.Update(counter, leds7);
    strip8.Update(counter, leds8);
    FastLED.show(); 
    counter++;
  }

  //Handle the crazy rollover case
  if (time > 4200967295){
    time = 0;
    oldTime = 0;
    counter = 0;
  }
}
