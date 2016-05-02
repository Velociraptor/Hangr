/*
Critical Making Provocation 3 - Dress Code - Spring 2016

This code runs on both Rfduino devices to receive commands to
turn on colors of the RGB LED from an Android application,
and to send a button press over BLE back to the app.

This is based on the example RFduino sketch "LEDButton" which demonstrates 
a full bi-directional Bluetooth Low Energy 4 connection between an iPhone 
application and an RFduino.
*/

/*
 Copyright (c) 2014 OpenSourceRF.com.  All right reserved.

 This library is free software; you can redistribute it and/or
 modify it under the terms of the GNU Lesser General Public
 License as published by the Free Software Foundation; either
 version 2.1 of the License, or (at your option) any later version.

 This library is distributed in the hope that it will be useful,
 but WITHOUT ANY WARRANTY; without even the implied warranty of
 MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 See the GNU Lesser General Public License for more details.

 You should have received a copy of the GNU Lesser General Public
 License along with this library; if not, write to the Free Software
 Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA  02110-1301  USA

 THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND,
 EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF
 MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT.
 IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY
 CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT,
 TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE
 SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
*/

#include <RFduinoBLE.h>

int ledR = 3;
int ledG = 2;
int ledB = 4;

// pin 5 is button 1
int button = 5;
int chosen = 0;

// debounce time (in ms)
int debounce_time = 10;

// maximum debounce timeout (in ms)
int debounce_timeout = 100;

void setup() {
  // led turned on/off from the app
  pinMode(ledR, OUTPUT);
  pinMode(ledG, OUTPUT);
  pinMode(ledB, OUTPUT);

  // button press will be sent to the app)
  pinMode(button, INPUT);

  // this is the data we want to appear in the advertisement
  // (if the deviceName and advertisementData are too long to fix into the 31 byte
  // ble advertisement packet, then the advertisementData is truncated first down to
  // a single byte, then it will truncate the deviceName)
  RFduinoBLE.advertisementData = "ledbtn";
  
  // start the BLE stack
  RFduinoBLE.advertisementData = "Hello1"; // shouldnt be more than 10 characters long 
  RFduinoBLE.deviceName = "Item1"; // name of your RFduino. Will appear when other BLE enabled devices search for it 
  RFduinoBLE.begin(); // begin
}

int debounce(int state)
{
  int start = millis();
  int debounce_start = start;
  
  while (millis() - start < debounce_timeout)
    if (digitalRead(button) == state)
    {
      if (millis() - debounce_start >= debounce_time)
        return 1;
    }
    else
      debounce_start = millis();

  return 0;
}

int delay_until_button(int state)
{
  // set button edge to wake up on
  if (state) {
    RFduino_pinWake(button, HIGH);
    chosen = 1;
  }
    
  else
    RFduino_pinWake(button, LOW);
    
  do
    // switch to lower power mode until a button edge wakes us up
    RFduino_ULPDelay(INFINITE);
  while (! debounce(state));
  
  // if multiple buttons were configured, this is how you would determine what woke you up
  if (RFduino_pinWoke(button))
  {
    // execute code here
    RFduino_resetPinWake(button);
  }
}

void loop() {
  delay_until_button(HIGH);
  RFduinoBLE.send(5);
  
  delay_until_button(LOW);

  if (!chosen) {
    digitalWrite(ledR, HIGH);
    Serial.print("Setting Red high");
  } else {
    digitalWrite(ledR, LOW);
    digitalWrite(ledG, LOW);
    digitalWrite(ledB, LOW);
    Serial.print("Setting low");
  }
}

void RFduinoBLE_onDisconnect()
{
  // don't leave the led on if they disconnect
  digitalWrite(ledR, LOW);
  digitalWrite(ledG, LOW);
  digitalWrite(ledB, LOW);
}

void RFduinoBLE_onReceive(char *data, int len)
{
  // match first byte received for color
  Serial.print("received data: ");
  Serial.print(data);
  if (data[0]==1) {
    Serial.print("Setting Red high");
    digitalWrite(ledR, HIGH);
  }
  else if (data[0]==2) {
    Serial.print("Red");
    digitalWrite(ledR, HIGH);
    digitalWrite(ledG, LOW);
    digitalWrite(ledB, LOW);
  }
  else if (data[0]==3) {
    Serial.print("Green");
    digitalWrite(ledR, LOW);
    digitalWrite(ledG, HIGH);
    digitalWrite(ledB, LOW);
  }
  else if (data[0]==4) {
    Serial.print("Blue");
    digitalWrite(ledR, LOW);
    digitalWrite(ledG, LOW);
    digitalWrite(ledB, HIGH);
  }    
  else {
    Serial.print("Setting all low (off)");
    digitalWrite(ledR, LOW);
    digitalWrite(ledG, LOW);
    digitalWrite(ledB, LOW);
  }
}
