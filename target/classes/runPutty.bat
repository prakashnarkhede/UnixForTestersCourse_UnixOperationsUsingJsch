:: update putty installation directory
:: used option -i to specify public key to access unix server
:: to specify port explicitly, use option -P followed by port number
:: if you use password instead, you can use option -pw followed by your password.
:: -no-antispoof    is used to get rid of message : Access granted. Press Return to begin session.
:: script.sh is actual shell script to run
:: pause is not required if not debuging
:: if you have a set of commands to execute (instead of shell script), you can write it in seperate text file and pass its location as said in below comment (where commands.txt is text file containing set of commands)
:: PLINK.exe -i G:\executeFromPutty\key.ppk prakash@34.68.117.110 -no-antispoof -ssh < G:\executeFromPutty\commands.txt
cd C:\Program Files\PuTTY
PLINK.exe -i G:\executeFromPutty\key.ppk prakash@34.68.117.110 -no-antispoof -ssh sh script.sh
pause
exit