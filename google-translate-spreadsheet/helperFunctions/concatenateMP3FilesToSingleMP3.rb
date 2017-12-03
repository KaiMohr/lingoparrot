
# http://lame.sourceforge.net/
# http://sox.sourceforge.net/


require './/helperFunctions/fileOperationsHelperFunctions'
require './/helperFunctions/speakOutLoudHelperFunction'


# quit unless our script gets a command line arguments
unless ARGV.length == 1
puts <<EOF
**********************
Dude, not the right number of arguments.
please insert the resulting name of the mp3 file
**********************
EOF
  speak_out_loud("Dude, not the right number of arguments. Please insert the resulting name of the mp3 file")
  exit
end

concatenatedFinishedFile = ARGV[0]

 sanitize_file_names
 make_files_AIF
 concatenate_AIF_to_temp_wave_file
 transcode_temp_file_to_MP3
 delete_files_AIF


speak_out_loud("i am finished now")




