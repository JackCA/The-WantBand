#include "WProgram.h"
#include "Button.h"
#include <MeetAndroid.h>

int Button::debounce;
MeetAndroid Button::meetAndroid;


Button::Button(int pin, int id){
  pinMode(pin,INPUT);
  _pin = pin;
  _id = id;
  _state = LOW;
  _reading = digitalRead(pin);
  _previous = HIGH;
  _time = 0;  
}; 

void Button::check(){
  _reading = digitalRead(_pin);
  if (_reading == HIGH && _previous == LOW && millis() - _time > debounce) {
    if (_state == HIGH){
      _state = LOW;
      //Serial.print(_pin);
      //Serial.print( "off"); quick fix 
      //Serial.print("on");
     // return String(_pin).concat("on");
     //return _pin;
      meetAndroid.send(_id);
    }
    else{
      _state = HIGH;
      //Serial.print(_pin);
     //Serial.print("on");
     //return String(_pin).concat("on");
     meetAndroid.send(_id);
  }

    _time = millis();    
  }

  _previous = _reading;
}
