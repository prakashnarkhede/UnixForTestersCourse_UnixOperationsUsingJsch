:: update putty installation directory
:: used option -i to specify public key to access unix server
:: to specify port explicitly, use option -P followed by port number
:: if you use password instead, you can use option -pw followed by your password.
:: -no-antispoof    is used to get rid of message : Access granted. Press Return to begin session.
:: script.sh is actual shell script to run
:: pause is not required if not debuging
:: command format is as below
:: pscp [options] source [source...] [user@]host:target
cd C:\Program Files\PuTTY
pscp.exe -i G:\executeFromPutty\key.ppk G:\executeFromPutty\FileToSendToHost.txt prakash@34.68.117.110:/home/prakash
exit