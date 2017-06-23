#include<SoftwareSerial.h>


int R = 7;
int G = 6;
int B = 3; // 3색 LED 아웃풋 포트 번호 설정
int BUZZ_OUT = 8 ; // 피에조 부저 아웃풋 포트 번호 설정

int blueTx=11; // 보내는 설정 Tranfer
int blueRx=10; // 받는 설정 Receiver
int BUTTON_IN = 12; // 풀업 스위치 인풋 포트 번호 설정
SoftwareSerial BTSerial(blueTx,blueRx); // 시리얼 통신을 위한 객체선언
int tones[] = { 523 , 587 , 659 , 698 , 784 , 880 , 988 , 1046 } ; 
// 피에조 부저 톤 스위치 설정


void setup() {
  // 시리얼 통신을 9600 속도로 한다.(개방)
  BTSerial.begin(9600); // BT 시리얼 통신을 9600
 
  pinMode(BUTTON_IN,INPUT_PULLUP); // 풀업저항을 사용 , LOW가 스위치가 눌림
  //digitalWrite(BUTTON_IN,HIGH); // 시스템 적 풀업 저항을 사용함으로써 새는 전류를 관리함 사용을 안하면 스위치 옆에 풀업용 저항을 따로 달아줘야 함.
  pinMode(R, OUTPUT);
  pinMode(G, OUTPUT);
  pinMode(B, OUTPUT);
  pinMode (BUZZ_OUT, OUTPUT);
}

void loop() {
  int cmd = 1;

  digitalWrite(R,HIGH);
  digitalWrite(G,LOW);     

  
  if(BTSerial.available()){ // 앱에서 어떠한 신호가 들어올때까지는 RED_LED를 킨다 이때는 준비 상태
    cmd = 0;
  }
 
  while(cmd == 0) {    // 신호가 들어와서 cmd=0이 되면 대기 상태로 버튼 입력을 기다림.
    //cmd = (char)BTSerial.read();
    //BTSerial.end();
    
    if (digitalRead(BUTTON_IN)==HIGH){ // 스위치가 눌린 상태가 아니라면 Green_led를 킨다.
      
        digitalWrite(R,LOW);
        digitalWrite(G,HIGH);       
    }  
      else if (digitalRead(BUTTON_IN)==LOW) { // 스위치가 눌리면 blue_led를 키고 소리와 앱으로 신호를 보낸다.
           BTSerial.println("push");
     
            digitalWrite(G ,LOW);
            delay(50);
            digitalWrite(B , HIGH);
            for(int i = 0 ; i < 3 ; i++ )
            {
              tone(BUZZ_OUT , tones[i]);
              delay(1000);
              noTone(BUZZ_OUT);
            }
    
            // delay(4000); // 버튼이 눌리면 20초간 버튼이 눌림 표시
            digitalWrite(B, LOW);    
            delay(100); // 부저 소리 내는 것   
          }
    }

 }
