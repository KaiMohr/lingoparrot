def speak_out_loud (sentence= "Default" , speed= "190", voice= "Tom")
  @voice = voice
  @sentence = sentence
  @speed = speed
  puts `say -v "#{voice}" -r "#{speed}" "#{sentence}"`
end
