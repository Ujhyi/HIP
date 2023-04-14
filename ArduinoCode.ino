#include <WiFi.h>
#include <Firebase_ESP_Client.h>
#include "addons/TokenHelper.h"
#include "addons/RTDBHelper.h"

#define API_KEY "AIzaSyDneucpLTuzrIuJT9_sg7KP-mvb9PrZddg"
#define DATABASE_URL "https://esp32v2-e8892-default-rtdb.firebaseio.com/"


FirebaseData fbdo;
FirebaseAuth auth;
FirebaseConfig config;

const char* ssid = "iPhone užívateľa David";
const char* password = "123456789";
unsigned long sendDataPrevMillis = 0;
bool signupOK = false;



void wifiConnection()
{
  WiFi.mode(WIFI_STA);
  WiFi.begin(ssid, password);
  Serial.println("Connection to WiFi ...");
  while (WiFi.status() != WL_CONNECTED)
  {
    Serial.print('.');
    delay(1000);
    
  }
  Serial.println("\n");
  Serial.println(WiFi.localIP());
}


void setup() {
  pinMode(26, INPUT);
  pinMode(39, INPUT);
  pinMode(17, INPUT);
  //LED
  pinMode(13, OUTPUT);
  pinMode(25, OUTPUT);
  pinMode(27, OUTPUT);

  Serial.begin(19200);

  wifiConnection();


  config.api_key = API_KEY;
  config.database_url = DATABASE_URL;
  if (Firebase.signUp(&config, &auth, "", ""))
  {
    Serial.println("signup OK");
    signupOK = true;
  }else {
    Serial.println("something is wrong");
  }

  config.token_status_callback = tokenStatusCallback;
  Firebase.begin(&config, &auth);
  Firebase.reconnectWiFi(true);


}

void loop() {

  bool value = digitalRead(26);
  bool value1 = digitalRead(39);
  bool value2 = digitalRead(17);


  if (value == 1)
  {
    Serial.println("EMPTY SPOT");
    digitalWrite(12, LOW);
    int value = 1;
  
  }else{
    Serial.println("NOT EMPTY SPOT");
    //digitalWrite(22, LOW);
    int value = 0;
    //String value = "false";
    digitalWrite(12, HIGH);

  }
  delay(1000);

  if (value1 == 1)
  {
    Serial.println("EMPTY SPOT");
    digitalWrite(25, LOW);
    //String value1 = "true";
    int value1 = 1;
  
  }else{
    Serial.println("NOT EMPTY SPOT");
    digitalWrite(25, HIGH);
    //String value1 = "false";
    int value1 = 0;

  }
  delay(1000);

  if (value2 == 1)
  {
    Serial.println("EMPTY SPOT");
    //digitalWrite(22, HIGH);
    //String value1 = "true";
    int value2 = 1;
    digitalWrite(13, LOW);
  
  }else{
    Serial.println("NOT EMPTY SPOT");
    //digitalWrite(22, LOW);
    int value2 = 0;
    digitalWrite(13, HIGH);
    
  }
  delay(1000);

  // put your main code here, to run repeatedly:
  if (Firebase.ready() && signupOK && (millis() - sendDataPrevMillis > 5000 || sendDataPrevMillis == 0))
  {
  
    sendDataPrevMillis = millis();

    if(Firebase.RTDB.setInt(&fbdo, "Parking1/ParkingSpot1", value))
    {
      Serial.println(); 
      Serial.println("PATH: " + fbdo.dataPath());
      Serial.println("TYPE: " + fbdo.dataType());
    }else {
      Serial.println("FAILED");

    }

    if(Firebase.RTDB.setInt(&fbdo, "Parking1/ParkingSpot2", value1))
    {
      Serial.println(); 
      Serial.println("PATH: " + fbdo.dataPath());
      Serial.println("TYPE: " + fbdo.dataType());
    }else {
      Serial.println("FAILED");

    }

    if(Firebase.RTDB.setInt(&fbdo, "Parking2/ParkingSpot1", value2))
    {
      Serial.println(); 
      Serial.println("PATH: " + fbdo.dataPath());
      Serial.println("TYPE: " + fbdo.dataType());
    }else {
      Serial.println("FAILED");

    }

  }
}




