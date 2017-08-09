SET /P _indirizzo= Inserire indirizzo IP server nella forma xxx.xxx.xxx.xxx:
SET /P _porta= Inserire porta TCP:
java -cp amr2fred.jar webDemo.Amr2FredWebDemo "%_indirizzo%" "%_porta%"
pause