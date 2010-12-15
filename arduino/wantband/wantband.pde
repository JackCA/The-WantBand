#include "Button.h";
#include <MeetAndroid.h>

  Button one (12,1);
  Button two (11,2);
  Button three (6,3);
  Button four (5,4);
  
void setup(){
  Serial.begin(57600); 
  Button::debounce = 200;
  digitalWrite(13, HIGH);   // set the LED on

};

void loop(){
  one.check();
  two.check();
  three.check();
  four.check();
}
