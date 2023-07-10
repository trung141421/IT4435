#mkdir mydockerbuild (create a directory named mydockerbuild)
#cd mydockerbuild (go to directory created above)
#vi Dockerfile (create a new docker file using vi editor)
FROM docker/whalesay:latest
RUN apt-get -y update && apt-get install -y fortunes
CMD /usr/games/fortune -a | cowsay