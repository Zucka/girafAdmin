from subprocess import Popen, PIPE, call
import os
from time import sleep


def writeHashes(developHash, masterHash):
	f = open('hashes', 'w')
	f.write(developHash+"\n"+masterHash)
	f.close()
	return

os.chdir("/home/neo/autoupdate/")

if not os.path.isdir('develop'):
	os.makedirs('develop')

if not os.path.isdir('master'):
	os.makedirs('master')

developHash = ''
masterHash = ''

if os.path.exists('hashes'):
	print 'Found hashes, reading hashes'
	f = open('hashes', 'r+')
	lines = f.readlines()
	f.close()
	developHash = lines[0].rstrip()
	masterHash = lines[1].rstrip()
else:
	print 'Did not find hashes, pulling current hashes, creating git folders and copying folders'
	developHash = Popen(['git ls-remote https://github.com/Zucka/girafAdmin.git | awk \'/develop/ {print $1}\''], stdout=PIPE, shell=True).stdout.read().rstrip()
	masterHash = Popen(['git ls-remote https://github.com/Zucka/girafAdmin.git | awk \'/master/ {print $1}\''], stdout=PIPE, shell=True).stdout.read().rstrip()
	writeHashes(developHash, masterHash)
	os.chdir("develop")
	call(['git init'], shell=True)
	call(['git remote add -t develop -f origin https://github.com/Zucka/girafAdmin.git'], shell=True)
	call(['git checkout develop'], shell=True)
	os.chdir("../master")
	call(['git init'], shell=True)
	call(['git remote add -t master -f origin https://github.com/Zucka/girafAdmin.git'], shell=True)
	call(['git checkout master'], shell=True)
	os.chdir("..")

	os.chdir("develop/source/desktop")
	call(['find . -depth -type f -not -iname \'db.php\' -print0 | cpio --null -pd /var/www/ '], shell=True)  # copy everything except db.php
	os.chdir("../../..")

while True:
	# print 'Checking for new changes'
	developHashNew = Popen(['git ls-remote https://github.com/Zucka/girafAdmin.git | awk \'/develop/ {print $1}\''], stdout=PIPE, shell=True).stdout.read().rstrip()
	masterHashNew = Popen(['git ls-remote https://github.com/Zucka/girafAdmin.git | awk \'/master/ {print $1}\''], stdout=PIPE, shell=True).stdout.read().rstrip()
	if developHash != developHashNew:
		print 'Develop branch hash is different, pulling new changes'
		print developHash
		print developHashNew
		os.chdir("develop")
		call(['git pull'], shell=True)
		os.chdir("..")
		developHash = developHashNew
		writeHashes(developHash, masterHash)
		os.chdir("develop/source/desktop")
		call(['find . -depth -type f -not -iname \'db.php\' -print0 | cpio --null -pd /var/www/ '], shell=True)  # copy everything except db.php
		os.chdir("../../..")
	if masterHash != masterHashNew:
		print 'Master branch hash is different, pulling new changes'
		print masterHash
		print masterHashNew
		os.chdir("master")
		call(['git pull'], shell=True)
		os.chdir('..')
		masterHash = masterHashNew
		writeHashes(developHash, masterHash)
	sleep(60)
