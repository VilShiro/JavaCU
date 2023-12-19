# for running project from /javaCU/, for example app "cjp"

# shellcheck disable=SC1036
# shellcheck disable=SC1088

#_%s_completions () {
#  local cur prev
#  cur="${COMP_WORDS[COMP_CWORD]}"
#  prev="${COMP_WORDS[COMP_CWORD-1]}"
#
#  local options="%s"
#  local keys="%s"
#  local functions="%s"
#
#  if [[ $cur == -* ]]; then
#    if [[ $cur == --* ]]; then
#      COMPREPLY=($(compgen -W "${keys}" -- ${cur}))
#    else
#      COMPREPLY=($(compgen -W "${options}" -- ${cur}))
#    fi
#  elif [[ $COMP_CWORD -eq 1 || -z $prev ]]; then
#    COMPREPLY=($(compgen -W "${functions}" -- ${cur}))
#  else
#    COMPREPLY=()
#  fi
#}
#
#complete -F _%s_completions %s

# for .inputrc
## javaCU

#TAB: menu-complete
#set show-all-if-ambiguous on
#set menu-complete-display-prefix on

## javaCU