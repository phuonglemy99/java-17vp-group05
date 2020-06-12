class Room {
    static socket;

    constructor(idroom)
    {
        this.idRoom = idroom;
        this.Player = [];
    }

    addPlayer(playerID){
        this.Player.push(playerID);
    }

    numPlayer(){
        return this.Player.length;
    }
}