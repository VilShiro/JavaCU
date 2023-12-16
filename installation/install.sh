# for running project from /javaCU/, for example app "cjp"
java -cp javacu.jar org.fbs.jcu.app.CJP

# for .bashrc
cjp(){
  java -cp javacu.jar org.fbs.jcu.app.CJP "$*"
}
