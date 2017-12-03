
def sanitize_file_names
  all_mp3_files= Dir.glob("*.mp3")
  all_mp3_files.each do |i|

    cuti = i.gsub(" ", "_")
    File.rename(i, cuti)

  end


end

def make_files_AIF
# transcoding existing mp3 files from a folder into several aif files
# store all files of the current directory, ending with .mp3 into all_mp3_files array

  all_mp3_files= Dir.glob("*.mp3")


# encoding all files as aif and cut off the last 3 characters of the file name and add .aif
# looping through the list of files

  all_mp3_files.each do |i|
    puts "encoding this file -> " + i

# i = i.gsub!(" ", "_")


# first parameter is the file name and the last the destination file name .aif
# the range  [0..-5] removes the file extension in the name abd gsub substitutes any space in the file name with an _ character
    puts `lame --decode "#{i}" "#{i[0..-5]}".aif`

# speak the file name without extension
#  puts `say  #{i[0..-5]}`
  end

end

def concatenate_AIF_to_temp_wave_file
# concatenate all aif files into a temp wave files
# the content of termline is later merged with make_temp_file_concatenate and printed into the terminal
  term_line = "sox "

  all_Aif_files_in_directory = Dir.glob("*.aif")
  all_Aif_files_amount = all_Aif_files_in_directory.size

  i = 0
  while i < all_Aif_files_amount
    term_line = term_line + "#{all_Aif_files_in_directory[i]}" + " "


    i += 1
  end



  make_temp_file_concatenate = "#{term_line}temp.wav --combine concatenate"
# compand 0.3,1 6:−70,−60,−20 −5 −90 0.2


  puts "#{make_temp_file_concatenate}"

 # puts `say combine files`
  puts `#{make_temp_file_concatenate}`

end

def transcode_temp_file_to_MP3
# transcode temp wav file to an mp3 file
  puts `say making m p three file`
# -a              downmix from stereo to mono file for mono encoding
#  -b <bitrate>    set the bitrate in kbps, default 128 kbps

# puts `lame temp.wav -ab 192k finished.mp3`

#
# --preset type   type must be "medium", "standard", "extreme", "insane",
# or a value for an average desired bitrate and depending
#              on the value specified, appropriate quality settings will
#              be used.
#


  concatenated_finished_file = ARGV[0]



  puts `lame temp.wav --preset cbr 192 #{concatenated_finished_file}`


  puts `say cleaning up`
# clean up
# delete the temp file
  puts `say deleting the temp file`

  begin
    File.delete("temp.wav")
  rescue Exception => e
    puts "error: " + e.to_s
    puts `say something went wrong please check file names`
    delete_files_AIF
  else
    puts `say temp file deleted`

  end


end

def delete_files_AIF
# delete all aif files
  puts `say deleting all the a i f files`
  all_Aif_files_in_directory = Dir.glob("*.aif")
  all_Aif_files_amount = all_Aif_files_in_directory.size
  i = 0
  while i < all_Aif_files_amount
    File.delete("#{all_Aif_files_in_directory[i]}")
    i += 1
  end
end

def delete_files_WAV
# delete all aif files
  puts `say deleting all the wave files`
  all_Aif_files_in_directory = Dir.glob("*.wav")
  all_Aif_files_amount = all_Aif_files_in_directory.size
  i = 0
  while i < all_Aif_files_amount
    File.delete("#{all_Aif_files_in_directory[i]}")
    i += 1
  end
end

