#include<FastLED.h>

#define DEFAULT_COLOR 0
#define ERROR_COLOR 0
#define DEFAULT_FADE 150
#define DEFAULT_RATE 2

class LEDstrip
{
  boolean state = false;
  boolean state2 = false;
  int numberLEDs;
  int kPattern;
  int kColor1;
  int kColor2;
  int kFade;
  int kRate;
   
  public:
  LEDstrip(int n)
  {
    numberLEDs = n;
    kPattern = 1;
    kColor1 = DEFAULT_COLOR;
    kColor2 = DEFAULT_COLOR;
    kFade = DEFAULT_FADE;
    kRate = DEFAULT_RATE;
  }

  void setNumLEDS(int n){
    numberLEDs = n;
  }

  void setLEDPattern(int p){
    kPattern = p;
  }
  int getLEDPattern(){
    return kPattern;
  }
  void setColor1(int c){
    kColor1 = c;
  }  
  int getColor1(){
    return kColor1;
  }
  void setColor2(int c){
    kColor2 = c;
  }
  void setFade(int f){
    kFade = f;
  }
  void setLEDRate(int r){
    kRate = r;
  }

  void ResetValues(){
    kPattern = 1;
    kColor1 = 192;
    kColor2 = 192;
    kFade = DEFAULT_FADE;
    kRate = DEFAULT_RATE;
  }
  void Update(int counter, CRGB l[]){
    Update(counter, l, kPattern, kColor1, kColor2, kRate, kFade);
  }
  //counter is an increment 
  void Update( int counter, CRGB l[], int pattern, int color1, int color2, int rate = DEFAULT_RATE, int fade = DEFAULT_FADE  )
  {
    //Counter increments once every 10ms, use counter for anything that needs to be timed base. Use % division to make it a certain amount of time
    //Make sequence increment up to number of LEDs and have it divide by the rate so it gets slower as rate goes up
    //state is when it's gone from the number of LEDs, so you can flip animations, etc
    int sequence = counter % (numberLEDs * rate); //sequence increments only to the number of leds
    if (sequence == 0){
       state = !state;
       if (state == false){
         state2 = !state2;
       }
    }
    sequence = sequence/ rate; //Lets you slow down the sequance based on the rate variable

    int saturation1 = 255;
    if(color1 == 255){
      saturation1 = 0;
    }
    int saturation2 = 255;
    if(color2 == 255){
      saturation2 = 0;
    }

    //This is where you can modify and add animtations to the systsm
    //Be sure to add them to Photon.java Animation enum if you want to call them from the RoboRIO
    switch(pattern){

      //Off
      case(0):
        for(int i = 0; i < numberLEDs; i++) {
          l[i] = CHSV(0, 0, 0);
        }
      break;
      
      //Solid Color
      case(1):
        for(int i = 0; i < numberLEDs; i++) {
          l[i] = CHSV(color1, saturation1, 255);
        }
      break;

      //Half Color
      case(2):
        for(int i = 0; i < numberLEDs /2; i++) {
          l[i] = CHSV(color1, saturation1, 255);
        }
        for(int i = numberLEDs / 2; i < numberLEDs; i++) {
          l[i] = CHSV(color2, saturation2, 255);
        }
      break;

      //Blink On/Off
      default:        //Sets the Default state to blinking of the default color, so you know something is wrong
        color1 = ERROR_COLOR;
        saturation1 = 255;
      case(3):
        if (state){
          for(int i = 0; i < numberLEDs; i++) {
            l[i] = CHSV(color1, saturation1, 255);
          }
        } else{
          for(int i = 0; i < numberLEDs; i++) {
            l[i] = CHSV(0, 0, 0);               //Off
          }
        }
      break;

      //Blink Color1/Color2
      case(4):
        if (state){
          for(int i = 0; i < numberLEDs; i++) {
            l[i] = CHSV(color1, saturation1, 255);
          }
        } else{
          for(int i = 0; i < numberLEDs; i++) {
            l[i] = CHSV(color2, saturation2, 255);
          }
        }
      break;

      //SIREN: Alternating half blinking alarm WIP
      case(5):
        if (state){
        for(int i = 0; i < numberLEDs /2; i++) {
          l[i] = CHSV(color1, saturation1, 255);
        }
        for(int i = numberLEDs / 2; i < numberLEDs; i++) {
          l[i] = CHSV(0, 0, 0);
        }
       }
       else{
        for(int i = 0; i < numberLEDs /2; i++) {
          l[i] = CHSV(0, 0, 0);
        }
        for(int i = numberLEDs / 2; i < numberLEDs; i++) {
          l[i] = CHSV(color2, saturation2, 255);
        }
       }

       break;

      //Odd/even
      case (6):
       if (state){
          for(int i =0; i < numberLEDs; i = i +2){
            l[i] = CHSV(color1, saturation1, 255);
          }
          for(int i =1; i < numberLEDs; i = i +2){
            l[i] = CHSV(color2, saturation2, 255);
          }
       } else{
          for(int i =0; i < numberLEDs; i = i +2){
            l[i] = CHSV(color2, saturation2, 255);
          }
          for(int i =1; i < numberLEDs; i = i +2){
            l[i] = CHSV(color1, saturation1, 255);
          }
       }
      break;

      //Theater Chase
      //Every third led on
      case (7):{
        for(int i = 0; i <numberLEDs; i = i+3){
          l[i+(sequence%3)] = CHSV(color1, saturation1, 255);
        }
        break;
      }

      //Reverse Theater Chase
      case (8):{
        for(int i = 2; i <numberLEDs; i = i+3){
          l[i-(sequence%3)] = CHSV(color1, saturation1, 255);
        }
        break;
      }
      
      //Pulse black to color
      case(10):
      {
          fade = 255; //Turn off the default Fade for this routine
          if (state){
            for(int i = 0; i < numberLEDs; i++) {
              l[i] = CHSV(color1, saturation1, ((sequence) * (255 / numberLEDs)));
            }
          } else {
            for(int i = 0; i < numberLEDs; i++) {
              l[i] = CHSV(color1, saturation1,255- ((sequence) * (255 / numberLEDs)));
            }
          }
      }
      break;

      //Pulse white to color
      case(11):
      {
          fade = 255; //Turn off the default Fade for this routine
          if (state){
            for(int i = 0; i < numberLEDs; i++) {
              l[i] = CHSV(color1, ((sequence) * (255 / numberLEDs)), 255);
            }
          } else {
            for(int i = 0; i < numberLEDs; i++) {
              l[i] = CHSV(color1, 255- ((sequence) * (255 / numberLEDs)),255);
            }
          }
      }
      break;

      
      //Fade in color1, fade out color2
      case(12):
      {
          fade = 255; //Turn off the default Fade for this routine
          if (state){
            for(int i = 0; i < numberLEDs; i++) {
              l[i] = CHSV(color1, saturation1, ((sequence) * (255 / numberLEDs)));
            }
          } else {
            for(int i = 0; i < numberLEDs; i++) {
              l[i] = CHSV(color2, saturation2,255- ((sequence) * (255 / numberLEDs)));
            }
          }
      }
      break;

      //Pulse black alternating between color1 and color2
      case(13):
      {
          fade = 255; //Turn off the default Fade for this routine
          if (!state2){
            if (state){
              for(int i = 0; i < numberLEDs; i++) {
                l[i] = CHSV(color1, saturation1,255-  ((sequence) * (255 / numberLEDs)));
              }
            } else {
              for(int i = 0; i < numberLEDs; i++) {
                l[i] = CHSV(color1, saturation1,((sequence) * (255 / numberLEDs)));
              }
            }
          } else{
             if (state){
              for(int i = 0; i < numberLEDs; i++) {
                l[i] = CHSV(color2, saturation2,255-  ((sequence) * (255 / numberLEDs)));
              }
            } else {
              for(int i = 0; i < numberLEDs; i++) {
                l[i] = CHSV(color2, saturation2,((sequence) * (255 / numberLEDs)));
              }
            }
          }
      }
      break;

      //Cylon 1 Color
      case(20):
        if (state){
          // Set the i'th led to color 1
          l[sequence] = CHSV(color1, saturation1, 255);
        } else{
          l[numberLEDs-sequence] = CHSV(color1, saturation1, 255);
        }
      break;
      
      //Cylon 2 Color
      case(21):
        if (state){
          // Set the i'th led to red 
          l[sequence] = CHSV(color1, saturation1, 255);
        } else{
          l[(numberLEDs-sequence)] = CHSV(color2, saturation2, 255);
        }
      break;

      //CylonBar 1 Color
      case(22):
        fade = 60; //set default fade to 60
        if (state){
          for(int i = sequence;i > sequence-4; i--){
            l[numberLEDs-i] = CHSV(color1, saturation1, 255);
          }
        } else{
          for(int i = sequence;i < sequence+4; i++){
            l[i] = CHSV(color1, saturation1, 255);
         }
        }
      break;

      //CylonBar 2 Color
      case(23):
        fade = 60; //set default fade to 60
        if (!state2){
          if (state){
            for(int i = sequence;i < sequence+4; i++){
              l[i] = CHSV(color1, saturation1, 255);
           }
          } else{
            for(int i = sequence;i > sequence-4; i--){
              l[numberLEDs-i] = CHSV(color1, saturation1, 255);
            }
          }
        } else {
          if (state){
            for(int i = sequence;i < sequence+4; i++){
              l[i] = CHSV(color2, saturation2, 255);
           }
          } else{
            for(int i = sequence;i > sequence-4; i--){
              l[numberLEDs-i] = CHSV(color2, saturation2, 255);
            }

          }          
        }
      break;

      //Cylon In from Sides
      case(24):
        if (state){
          l[sequence / 2] = CHSV(color1, saturation1, 255);
          l[numberLEDs - (sequence/2)] = CHSV(color1, saturation1, 255);
        } else {
          l[numberLEDs/2 - sequence / 2] = CHSV(color1, saturation1, 255);
          l[numberLEDs/2 + (sequence/2)] = CHSV(color1, saturation1, 255);
        }
      break;

      //Cylon In from Sides Dual
      case(25):
        if (!state2){
          if (!state){
            l[sequence / 2] = CHSV(color1, saturation1, 255);
            l[numberLEDs - (sequence/2)] = CHSV(color1, saturation1, 255);
          } else {
            l[numberLEDs/2 - sequence / 2] = CHSV(color1, saturation1, 255);
            l[numberLEDs/2 + (sequence/2)] = CHSV(color1, saturation1, 255);
          }
        } else {
           if (!state){
            l[sequence / 2] = CHSV(color2, saturation2, 255);
            l[numberLEDs - (sequence/2)] = CHSV(color2, saturation2, 255);
          } else {
            l[numberLEDs/2 - sequence / 2] = CHSV(color2, saturation2, 255);
            l[numberLEDs/2 + (sequence/2)] = CHSV(color2, saturation2, 255);
          }         
        }
      break;
      
      //TRACER
      case(30):
        l[sequence] = CHSV(color1, saturation1, 255);
      break;

      //TRACER_ALTERNATE - 2 Colors
      case(31):
        if (state){
          // Set the sequence led to color1 
          l[sequence] = CHSV(color1, saturation1, 255);
        } else{ //set the sequence number to color2
          l[sequence] = CHSV(color2, saturation2, 255);
        }
      break;

      //WIPE_FWD
      case(32):
        for(int i = 0; i <= sequence; i++){
          l[i] = CHSV(color1, saturation1, 255);
        }
      break;

      //WIPE_REV
      case(33):
        for(int i = numberLEDs; i >= numberLEDs - sequence; i--){
          l[i-1] = CHSV(color1, saturation1, 255);
        }
      break;
      
      //WIPE_FWD_REV
      case(34):
        if (state){
          // Set the i'th led to red 
          for(int i = 0; i <= sequence; i++){
            l[i] = CHSV(color1, saturation1, 255);
          }
        } else{
          for(int i = numberLEDs; i >= numberLEDs - sequence; i--){
            l[i-1] = CHSV(color1, saturation1, 255);
          }
        }
      break;

      //WIPE_DOWN
      case(35):
        if (sequence != 0){
          for(int i = 0; i <= numberLEDs - sequence; i++){
            l[i] = CHSV(color1, saturation1, 255);
          }
        }
      break;

      //WIPE_UP_DOWN
      case(36):
        if (state){
          for(int i = 0; i <= sequence; i++){
            l[i] = CHSV(color1, saturation1, 255);
          }
        } else{
          for(int i = 0; i <= numberLEDs - sequence; i++){
            l[i] = CHSV(color1, saturation1, 255);
          }
        }
      break;

      //WIPE_UP_DOWN_DUAL
      case(37):
        if (state){
          // Set the i'th led to red 
          for(int i = 0; i <= sequence; i++){
            l[i] = CHSV(color1, saturation1, 255);
          }
          for(int i = sequence; i < numberLEDs; i++){
            l[i+1] = CHSV(color2, saturation2, 255);
          }
        } else{
          for(int i = 0; i <= numberLEDs - sequence; i++){
            l[i] = CHSV(color1, saturation1, 255);
          }
          for(int i = numberLEDs - sequence; i < numberLEDs; i++){
            l[i+1] = CHSV(color2, saturation2, 255);
          }
        }
      break;

      
      //WIPE_IN
      case(40):
          for(int i = 0; i <= sequence / 2; i++){
            l[i] = CHSV(color1, saturation1, 255);
          }
          for(int i = numberLEDs; i >= numberLEDs - (sequence/2); i--){
            l[i-1] = CHSV(color1, saturation1, 255);
          }
      break;

      //WIPE_OUT
      case(41):
          for(int i = numberLEDs/2; i >= numberLEDs/2 - (sequence/2); i--){
            l[i] = CHSV(color1, saturation1, 255);
          }
          for(int i = numberLEDs/2; i <= numberLEDs/2 + (sequence/2); i++){
            l[i] = CHSV(color1, saturation1, 255);
          }
      break;
      
      //WIPE_IN_OUT
      case(42):
        if (state){
          for(int i = 0; i <=sequence / 2; i++){
            l[i] = CHSV(color1, saturation1, 255);
          }
          for(int i = numberLEDs; i >= numberLEDs - (sequence/2); i--){
            l[i-1] = CHSV(color1, saturation1, 255);
          }
        } else{
          for(int i = 0; i <= (numberLEDs - sequence) / 2; i++){
            l[i] = CHSV(color1, saturation1, 255);
          }
          for(int i = numberLEDs; i >= numberLEDs/2 + (sequence/2); i--){
            l[i-1] = CHSV(color1, saturation1, 255);
          }
        }
      break;

      //WIPE_IN_OUT_BACK
      case(43):
      if (!state2){
        if (!state){
          for(int i = 0; i <=sequence / 2; i++){
            l[i] = CHSV(color1, saturation1, 255);
          }
          for(int i = numberLEDs; i >= numberLEDs - (sequence/2); i--){
            l[i-1] = CHSV(color1, saturation1, 255);
          }
        } else{
          for(int i = numberLEDs/2; i >= sequence/2; i--){
            l[i] = CHSV(color1, saturation1, 255);
          }
          for(int i = numberLEDs/2; i <= numberLEDs - sequence/2; i++){
            l[i] = CHSV(color1, saturation1, 255);
          }
        }
      } else {
        if (!state){
          for(int i = numberLEDs/2; i >= numberLEDs/2 - (sequence/2); i--){
            l[i] = CHSV(color1, saturation1, 255);
          }
          for(int i = numberLEDs/2; i <= numberLEDs/2 + (sequence/2); i++){
            l[i] = CHSV(color1, saturation1, 255);
          }
        } else{
          for(int i = 0; i <= (numberLEDs - sequence) / 2; i++){
            l[i] = CHSV(color1, saturation1, 255);
          }
          for(int i = numberLEDs; i >= numberLEDs/2 + (sequence/2); i--){
            l[i-1] = CHSV(color1, saturation1, 255);
          }
        }
      }
      break;
      
      //rainbow
      case(96):{
        for(int i = 0; i < numberLEDs; i++){
          //Shift more red into the rainbow and removes some of the pink at the top end
          int c = (235*((sequence+i)%numberLEDs))/numberLEDs;
          c = c - 20;
          if (c < 0){
            c = 0;
          }
          l[i] = CHSV(c,255, 255);
        }
      break;
      }
      
      //Percentage
      case(97):{
        int p = color2;
        if (p > 100){
          p = 100;
        }
        for(int i = 0; i <= (numberLEDs * p) / 100 ; i++){
          l[i] = CHSV(color1, saturation1, 255);
        }
      break;
      }
      
      //Juggle: eight colored dots moving out of sync
      case(98):{
      // eight colored dots, weaving in and out of sync with each other
        fadeToBlackBy(l, numberLEDs, 20);
        byte dothue = 0;
        for( int i = 0; i < 8; i++) {
          l[beatsin16( i+7, 0, numberLEDs-1 )] |= CHSV(dothue, 200, 255);
          dothue += 32;
        }
      break;
      }

      //Random Sparkles that blink in and fade out smoothly
      case(99):
      {
        if (sequence == 1){
          int pos = random16(numberLEDs);
          l[pos] += CHSV( color1 + random8(64), 200, 255);
        }
      break;
      }
    }


    
    /////////////////////////////////////////////////////////
    //DO NOT DELETE /////////////////////////////////////////
    //Fade Everything at the End
    //if (fade != 0){
      for(int i = 0; i < numberLEDs; i++) { 
         l[i].nscale8(fade); } 
    //}
    //////////////////////////////////

  }

};
