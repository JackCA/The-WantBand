#ifndef Button_h
#define Button_h

#include "WProgram.h"
#include <MeetAndroid.h>

class Button{
  public:
    Button(int pin, int id);
    void check();
    static int debounce;  
  private:
    int _pin;
    int _id;
    int _state;
    int _reading;
    int _previous;
    long _time;
    static MeetAndroid meetAndroid;
};



#endif

