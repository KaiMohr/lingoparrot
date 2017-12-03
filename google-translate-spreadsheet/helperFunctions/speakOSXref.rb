

##################################################################
# install mac ports or a similar package install manager
# install sox
# install ffmpeg
# port search --name 'ffmpeg*'
# using lame in the code below that comes with the ffmpeg install
##################################################################



require_relative '../helperFunctions/fileOperationsHelperFunctions'
require_relative '../helperFunctions/speakOutLoudHelperFunction'



readingSpeedES = 140
readingSpeedDE = 180
readingSpeedEN = 140

spanishVoice = "Jorge"
germanVoice  = "Markus"
englishVoice ="Susan"

# the project path
myProjectFolder = File.dirname(__FILE__)


# quit unless our script gets two command line arguments
unless ARGV.length == 3

  puts <<prttouser
Dude, not the right number of arguments.
prttouser

# Do it like this: ruby Ruby.rb "se va a casa" "Sie geht nach hause" "she goes home"
  exit
end


spanishText = ARGV[0]
germanText = ARGV[1]
englishText = ARGV[2]


languageFileES = "#{spanishText[0,20].chomp}"
languageFileEN = "#{englishText[0,20].chomp}"
languageFileDE = "#{germanText[0,20].chomp}"



# We let the mac speak the text into temp aiff files
def speakIntoFiles(spanishText, germanText, englishText)
  @spanishText = spanishText
  @germanText  = germanText
  @englishText = englishText


  readingSpeedES = 140
  readingSpeedDE = 180
  readingSpeedEN = 140

  spanishVoice = "Jorge"
  germanVoice  = "Markus"
  englishVoice ="Susan"

  # we set the character length of the file name
  # languageFileDE = "#{germanText[0,55]}"
  languageFileES = "#{spanishText[0,20].chomp}"
  languageFileEN = "#{englishText[0,20].chomp}"
  languageFileDE = "#{germanText[0,20].chomp}"
puts `say -v #{spanishVoice} -r #{readingSpeedES} "#{spanishText}" -o "#{languageFileES}".aif`
puts `say -v #{germanVoice}  -r #{readingSpeedDE} "#{germanText}"  -o "#{languageFileDE}".aif`
puts `say -v #{englishVoice}  -r #{readingSpeedEN} "#{englishText}"  -o "#{languageFileEN}".aif`
end

def createCombinedFiles(spanishText, germanText, englishText)

  @spanishText = spanishText
  @germanText  = germanText
  @englishText = englishText

  # we set the character length of the file name
  # languageFileDE = "#{germanText[0,55]}"
  languageFileES = "#{spanishText[0,20].chomp}"
  languageFileEN = "#{englishText[0,20].chomp}"
  languageFileDE = "#{germanText[0,20].chomp}"


# we write the sound files into a one single wav file and create a second file where the order of languages is changed
# we write the German files
puts `sox  "#{languageFileDE}".aif "#{languageFileES}".aif "#{languageFileEN}".aif "#{languageFileDE}".wav --combine concatenate`
#we write them twisted
puts `sox  "#{languageFileES}".aif "#{languageFileEN}".aif "#{languageFileES}".aif "#{languageFileDE}"2.wav --combine concatenate`


# and  make an mp3 file of both
puts `lame "#{languageFileDE}".wav -ab 192k "#{languageFileDE}".mp3`
puts `lame "#{languageFileDE}"2.wav -ab 192k "#{languageFileDE}"2.mp3`

# we write the spanish files
puts `sox  "#{languageFileES}".aif "#{languageFileDE}".aif "#{languageFileEN}".aif "#{languageFileES}".wav --combine concatenate`

end

def createCombinedFilesTwisted(spanishText, germanText, englishText)

  @spanishText = spanishText
  @germanText  = germanText
  @englishText = englishText

  # we set the character length of the file name
  # languageFileDE = "#{germanText[0,55]}"
  languageFileES = "#{spanishText[0,20].chomp}"
  languageFileEN = "#{englishText[0,20].chomp}"
  languageFileDE = "#{germanText[0,20].chomp}"

# and then write them twisted first german then spanish as a single wav file and rename the file by adding the number 2
puts `sox  "#{languageFileEN}".aif "#{languageFileDE}".aif "#{languageFileES}".aif "#{languageFileES}"2.wav --combine concatenate`
# and  make an mp3 file of both
puts `lame "#{languageFileES}".wav -ab 192k "#{languageFileES}".mp3`
puts `lame "#{languageFileES}"2.wav -ab 192k "#{languageFileES}"2.mp3`

# we write the English files
puts `sox  "#{languageFileEN}".aif "#{languageFileES}".aif "#{languageFileDE}".aif "#{languageFileEN}".wav --combine concatenate`
#we write them twisted
puts `sox  "#{languageFileDE}".aif "#{languageFileES}".aif "#{languageFileEN}".aif "#{languageFileEN}"2.wav --combine concatenate`
# and  make an mp3 file of both
puts `lame "#{languageFileEN}".wav -ab 192k "#{languageFileEN}".mp3`
puts `lame "#{languageFileEN}"2.wav -ab 192k "#{languageFileEN}"2.mp3`


end

def moveFilesToLanguageFolders(spanishText, germanText, englishText)

  @spanishText = spanishText
  @germanText  = germanText
  @englishText = englishText

  # we set the character length of the file name
  # languageFileDE = "#{germanText[0,55]}"
  languageFileES = "#{spanishText[0,20].chomp}"
  languageFileEN = "#{englishText[0,20].chomp}"
  languageFileDE = "#{germanText[0,20].chomp}"

  # the project path
  myProjectFolder = File.dirname(__FILE__)

  # and move the files in the folders DE, ES, EN and mp3

  #DE
  puts `mv #{myProjectFolder}/"#{languageFileDE}".mp3 #{myProjectFolder}/exports/DE/mp3/"#{languageFileDE}".mp3`
  puts `mv #{myProjectFolder}/"#{languageFileDE}"2.mp3 #{myProjectFolder}/exports/DE/mp3/"#{languageFileDE}"2.mp3`
  #ES
  puts `mv #{myProjectFolder}/"#{languageFileES}".mp3 #{myProjectFolder}/exports/ES/mp3/"#{languageFileES}".mp3`
  puts `mv #{myProjectFolder}/"#{languageFileES}"2.mp3 #{myProjectFolder}/exports/ES/mp3/"#{languageFileES}"2.mp3`
  #EN
  puts `mv #{myProjectFolder}/"#{languageFileEN}".mp3 #{myProjectFolder}/exports/EN/mp3/"#{languageFileEN}".mp3`
  puts `mv #{myProjectFolder}/"#{languageFileEN}"2.mp3 #{myProjectFolder}/exports/EN/mp3/"#{languageFileEN}"2.mp3`
  #
  speak_out_loud("Files moved")
end


speakIntoFiles(spanishText, germanText, englishText)

createCombinedFiles(spanishText, germanText, englishText)

createCombinedFilesTwisted(spanishText, germanText, englishText)


# then we delete the original files
delete_files_AIF
delete_files_WAV

# and moved the files to the folders /ES /DE /EN
moveFilesToLanguageFolders(spanishText, germanText, englishText)






# # Now we write the language texts into a comma separated file
#
# # the german text
# File.open("#{myProjectFolder}/cvs/#{languageFileDE}.cvs", 'w') { |file| file.write("#{englishText},#{spanishText},#{germanText}") }
# # the english text twisted
# # File.open("#{myProjectFolder}/cvs/#{languageFileDE}2.cvs", 'w') { |file| file.write("#{spanishText},#{germanText},#{languageFileEN}") }
#
# # the spanish text
# File.open("#{myProjectFolder}/cvs/#{languageFileES}.cvs", 'w') { |file| file.write("#{englishText},#{spanishText},#{germanText}") }
# # the english text twisted
# # File.open("#{myProjectFolder}/cvs/#{languageFileES}2.cvs", 'w') { |file| file.write("#{germanText},#{spanishText},#{englishText}") }
#
# # the english text
# File.open("#{myProjectFolder}/cvs/#{languageFileEN}.cvs", 'w') { |file| file.write("#{englishText},#{spanishText},#{germanText}") }
# # the english text twisted
# # File.open("#{myProjectFolder}/cvs/#{languageFileEN}2.cvs", 'w') { |file| file.write("#{germanText},#{spanishText},#{englishText}") }
#


# finally we play the english file
puts `afplay #{myProjectFolder}/EN/mp3/"#{languageFileEN}"2.mp3`

