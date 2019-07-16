:: update puttu installation directory
:: used option -i to specify public key to access unix server
:: to specify port explicitly, use option -P followed by port number
:: if you use password instead, you can use option -pw followed by your password.
:: -no-antispoof    is used to get rid of message : Access granted. Press Return to begin session.
:: pause is not required if not debuging
:: command syntax in on bleow line
:: pscp [Options] fred@example.com:/etc/hosts c:\temp\example-hosts.txt
cd C:\Program Files\PuTTY
pscp.exe -i G:\executeFromPutty\key.ppk prakash@34.68.117.110:/home/prakash/outputFile.txt G:\executeFromPutty\outputFile.txt
exit