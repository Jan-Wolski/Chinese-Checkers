create table Game(
  id int unsigned,
  players_number int unsigned
);

create table Players(
  game_id int unsigned,
  player_number int unsigned,
  nickname varchar(20)
);

create table Turns(
  game_id int unsigned,
  turn_id int unsigned,
  field_one int unsigned,
  field_two int unsigned
);