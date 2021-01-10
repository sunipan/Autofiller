## Information About Autofiller
My first program, it can store passwords in a file and encrypt the file automatically. It can also retrieve a password that was saved to file by receiving the username for the password as input. It copies the password to your clipboard for easy pasting. I've also implemented a remove password method which takes a username as input. Notice the deletePass method only deletes the first instance of the username linked with a password. If you've stored more than one password with the same username you'll have to run this remove method more than once.

Later on I plan on having a key generator do all the work to make the files even more secure, but for now it only uses a static 16 bit key to encrypt the files using AES.
## Installation
Simply download the .exe and run it. The encrypted password file will be created in the same directory as the .exe file. If you prefer the .jar, you will need to install the Java JRE to run it.
