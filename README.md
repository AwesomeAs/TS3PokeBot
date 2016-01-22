# TS3PokeBot
Kristian wants a bot to poke his friend on TeamSpeak 3 to troll him.

Adjustable variables in the program
| Variable      | Description |
| ------------- | ------------- |
| HOST          | The server IP we connect to |
| PORT          | The server port we connect to |
| BOTNAME       | The username for our bot. |
| POKEMSG       | The message to be poked |
| TARGETID      | Our target client's ID. Use TARGETNAME if you do not know the ID. |
| TARGETNAME    | Setting this to anything other than null will update TARGETID to the desired ID if found. |
| POKEAMOUNT    | Extra: Let the bot poke MULTIPLE times! Wow! |
| POKEINTERVAL  | Extra: ^ Poking multiple times comes in intervals! |
| CHANNELID     | Make the bot join a channel, if the value is not 0. |

Makes use of https://github.com/TheHolyWaffle/TeamSpeak-3-Java-API
