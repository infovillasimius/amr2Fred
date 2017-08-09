#!/bin/bash
echo "Inserire indirizzo IP server nella forma xxx.xxx.xxx.xxx "
read indirizzo
echo "Inserire il numero della porta TCP "
read porta
java -cp amr2fred.jar webDemo.Amr2FredWebDemo $indirizzo $porta
