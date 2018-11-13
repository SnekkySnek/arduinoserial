
#include <SPI.h>
#include "mcp_can.h"
#include <LiquidCrystal.h>




LiquidCrystal lcd(8, 7, 6, 5, 4, 3);

const int SPI_CS_PIN = 9;




void setup() {
  // put your setup code here, to run once:

Serial.begin(9600);
  lcd.begin(16,2);

    while (CAN_OK != CAN.begin(CAN_500KBPS))              // init can bus : baudrate = 500k
    {
        Serial.println("CAN BUS Shield init fail");
        Serial.println(" Init CAN BUS Shield again");
        delay(100);
    }

  lcd.setCursor(0,0);
  lcd.print("CAN BUS Shield init ok!");
 

}

void loop() {
  // put your main code here, to run repeatedly:

  if(rfid.PICC_IsNewCardPresent()){
    readRFID();
  }
  delay(100);
}

void readRFID(){
  rfid.PICC_ReadCardSerial();
  Serial.print(F("\nPICC type: "));
  MFRC522::PICC_Type piccType = rfid.PICC_GetType(rfid.uid.sak);
  lcd.setCursor(0, 1);
  lcd.print(rfid.PICC_GetTypeName(piccType));
  
}
