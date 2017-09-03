SET /P _input_file= Inserire nome file input:
SET /P _output_file= Inserire nome file output:
java -cp amr2fred.jar fileConvert.Amr2File "%_input_file%" "%_output_file%"
pause