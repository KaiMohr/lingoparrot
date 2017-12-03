require 'bundler'
Bundler.require
 require_relative '../spreadsheet_fun/helperFunctions/fileOperationsHelperFunctions'
 require_relative '../spreadsheet_fun/helperFunctions/speakOutLoudHelperFunction'
 # require_relative '../spreadsheet_fun/helperFunctions/speakOSXref'

 def truncate(truncate_at, options = {})
  return dup unless length > truncate_at

  options[:omission] ||= '...'
  length_with_room_for_omission = truncate_at - options[:omission].length
  stop =        if options[:separator]
      rindex(options[:separator], length_with_room_for_omission) || length_with_room_for_omission
    else
      length_with_room_for_omission
    end

  "#{self[0...stop]}#{options[:omission]}"
 end
 readingSpeedES = 140
 readingSpeedEN = 140

 spanishVoice = "Jorge"
 englishVoice ="Susan"
# Authenticate a session with your Service Account
session = GoogleDrive::Session.from_service_account_key("client_secret.json")
session.files.each do |file|
  p file.title
  p file.human_url
end
# Spanisch, Englisch, Está lloviendo a mares.  la lluvia, It's raining buckets.
# Get the spreadsheet by its title
spreadsheet = session.spreadsheet_by_title("Wortschatz_17112017")
# Get the first worksheet
worksheet = spreadsheet.worksheets.first
    puts "maximum rows" 
    puts worksheet.max_rows
    puts worksheet.title
    puts "updated"
    puts worksheet.updated
    readingSpeedES = 140
    readingSpeedEN = 140
    worksheet.rows.each { |row| 
    english = row.at(1)

# lets check what language
if english == "Englisch" 
    englishText = row.last(1).join(", ")
    puts englishText
#   puts `say -v #{englishVoice}  -r #{readingSpeedEN} "#{englishText}"`
    englishText = row.at(3)
    spanishText = row.at(2)
    spanishFileName = spanishText.tr(' ', '-').slice(0..7)
    englishFileName = englishText.tr(' ', '-').slice(0..7)

#   puts `say -v #{spanishVoice} -r #{readingSpeedES} "#{spanishText}"`
    puts `say -v #{englishVoice} -r #{readingSpeedES} "#{englishText}" -o "#{englishFileName}-#{spanishFileName}".aif` 
    puts `say -v #{spanishVoice} -r #{readingSpeedES} "#{spanishText}" -o "#{spanishFileName}-#{englishFileName}".aif` 
    concatenate_AIF_to_temp_wave_file  
    
    else
    spanishText = row.at(3)
    englishText = row.at(2)
    spanishFileName = spanishText.tr(' ', '-').slice(0..7)
    englishFileName = englishText.tr(' ', '-').slice(0..7)

    puts englishFileName
puts spanishFileName

    puts spanishText
#   puts `say -v #{spanishVoice} -r #{readingSpeedES} "#{spanishText}"`
    puts englishText
#   puts `say -v #{englishVoice} -r #{readingSpeedEN} "#{englishText}"`
    puts `say -v #{englishVoice}  -r #{readingSpeedEN} "#{englishText}"  -o "#{englishFileName}-#{spanishFileName}".aif`
    puts `say -v #{spanishVoice}  -r #{readingSpeedEN} "#{spanishText}"  -o "#{spanishFileName}-#{englishFileName}".aif`
end

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
    puts `#{make_temp_file_concatenate}`

   # puts `lame temp.wav --preset cbr 192 #{spanishText}.mp3`
    puts `lame temp.wav --preset cbr 30 #{spanishFileName}.mp3`
 
    # delete all aif files
    # puts `say deleting all the a i f files`
    all_Aif_files_in_directory = Dir.glob("*.aif")
    all_Aif_files_amount = all_Aif_files_in_directory.size
    i = 0
  while i < all_Aif_files_amount
    File.delete("#{all_Aif_files_in_directory[i]}")
    i += 1
  end
    }

#   delete the temp file
  begin
    File.delete("temp.wav")
  rescue Exception => e
    puts "error: " + e.to_s
    puts `say something went wrong please check file names`
  else
  # puts `say temp file deleted`
  end
 # puts worksheet[2, 3]
 # spreadsheet.export_as_file("testing.csv")
 # download_to_file (keeps the original format)
