


// Commands we send from the PC and want to receive on the Arduino.
// We must define a callback function in our Arduino program for each entry in the list below.

void attachCommandCallbacks()
{
  // Attach callback methods
  cmdMessenger.attach(OnUnknownCommand);
  cmdMessenger.attach(kSetStrip, OnSetStrip);
  cmdMessenger.attach(kSetNumLEDSstrip, OnSetNumLEDStrip);
}

// ------------------  C A L L B A C K S -----------------------

// Called when a received command has no attached function
void OnUnknownCommand()
{
  cmdMessenger.sendCmd(kError,"Command without attached callback");
}

// Callback function that responds that Arduino is ready (has booted up)
void OnArduinoReady()
{
  cmdMessenger.sendCmd(kAcknowledge,"Arduino ready");
}

// Callback function sets the LEDstrip parameters
void OnSetStrip()
{
  LEDstrip* strip;
  // Retreive first parameter as float
  int s = cmdMessenger.readInt32Arg();

  switch(s){
    case(1):{
      strip = &strip1;
      Serial.println ("Setting Strip 1");
    }
    break;
    case(2):{
      strip = &strip2;
    }
    break;
    case(3):{
      strip = &strip3;
    }
    break;
    case(4):{
      strip = &strip4;
    }
    break;
    case(5):{
      strip = &strip5;
    }
    break;
    case(6):{
      strip = &strip6;
    }
    break;
    case(7):{
      strip = &strip7;
    }
    break;
    case(8):{
      strip = &strip8;
    }
    break;
    default:
     strip = NULL;
  }

  if (strip != NULL){
  
    //Reset the strip values to the defaults
    strip->ResetValues();
  
    //Read in the next argument and if it's valid set the LED strip pattern
    int p = cmdMessenger.readInt16Arg();
    if (cmdMessenger.isArgOk()) {
      strip->setLEDPattern(p);
    }
    
    int c1 = cmdMessenger.readInt16Arg();
    if (cmdMessenger.isArgOk()) {
      strip->setColor1(c1);
    }
    
    int c2 = cmdMessenger.readInt16Arg();
    if (cmdMessenger.isArgOk()) {
      strip->setColor2(c2);
    }

    int r = cmdMessenger.readInt16Arg();
    if (cmdMessenger.isArgOk()) {
      strip->setLEDRate(r);
    }
    
    int f = cmdMessenger.readInt16Arg();
    if (cmdMessenger.isArgOk()) {
      strip->setFade(f);
    }
    

  
    // Send back the Acknowledgement
    cmdMessenger.sendCmd(kAcknowledge,"LED Strip Values Set");
  } else {
    cmdMessenger.sendCmd(kError,"STRIP Number Out of Range");
  }
}

void OnSetNumLEDStrip(){
  LEDstrip* strip;
  // Retreive first parameter as float
  int s = cmdMessenger.readInt32Arg();

  switch(s){
    case(1):{
      strip = &strip1;
    }
    break;
    case(2):{
      strip = &strip2;
    }
    break;
    case(3):{
      strip = &strip3;
    }
    break;
    case(4):{
      strip = &strip4;
    }
    break;
    case(5):{
      strip = &strip5;
    }
    break;
    case(6):{
      strip = &strip6;
    }
    break;
    case(7):{
      strip = &strip7;
    }
    break;
    case(8):{
      strip = &strip8;
    }
    break;
    default:
      strip = NULL;
  }
  if (strip != NULL){
    int n = cmdMessenger.readInt16Arg();
    if (cmdMessenger.isArgOk()) {
      strip->setNumLEDS(n);
    }
  
      // Send back the Acknowledgement
    cmdMessenger.sendCmd(kAcknowledge,"Number of LEDS Set");
  } else {
    cmdMessenger.sendCmd(kError,"LED Strip Out of Range");
  }
}
