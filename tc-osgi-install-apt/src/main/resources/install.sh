#!/bin/bash
# Auteurs     : Thomas Collonvillé 
# Date        : 05/04/2017
# Fichier     : install.sh 
# version     : 0.1.0
# Licence     : GNU GENERAL PUBLIC LICENSE (Version 3, 29 June 2007)
# url licence : http://www.gnu.org/licenses/gpl.txt
# object      : 0.1.0   script d'installation du contenu du fichier tar.gz.list


FILE_LIST="tar.gz.list"
cyg_target_dir="/cygdrive/d"
repository_dir="$cyg_target_dir/Works/targz"
targz_directory="$cyg_target_dir/targz"
install_directory="$cyg_target_dir/opt"

mkdir -p $targz_directory



printContentArchivdir()
{
  echo "Archive targz content: $1"
  ls -lh "$1"
}

extractPass()
{
  pushd "$1"
  for f in $(/bin/ls); 
  do 
    7z x "$f" -y | grep -i -e "Everything is Ok" -e "Extracting.*tar"; 
  done
  popd
}

printTreeInstall()
{
  pushd "$1"
  tree.com
  #ls -lhR 
  popd
}

extractAll()
{
  echo "Extract archive first pass" 
  extractPass "$1"
  echo "Extract archive second pass"
  extractPass "$1"
}

reinstallCygwin()
{
  echo "Nettoyage du repertoire d'installation $2"
  pushd /
  ln -fs "$2"
  popd
  rm -R "$2"
  printContentArchivdir "$1" 
  extractAll "$1"
  mv "$1/opt" "$2"
  printTreeInstall "$2"
} 

prepareInstall()
{
  echo "Preparation de l'installation "
  while read line; 
  do 
    echo "Copy de $1/$line dans $2/"
    cp $1/$line $2/
  done < $3
}

prepareInstall "$repository_dir" "$targz_directory" "$FILE_LIST"
reinstallCygwin "$targz_directory" "$install_directory"

