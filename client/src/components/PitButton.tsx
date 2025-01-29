import classNames from "classnames";
import { playPit} from "../services/api";
import { useMancalaGame } from "../contexts/MancalaGameContext";
import { useState } from "react";
import { startGame } from "../services/api";
import { GameState, isGameState } from "../types";
import styles from '../Play.module.css';

type Props = {
    nrOfStones: number;
    index: number;
    isActive : boolean;
};
export const PitButton = (props: Props) => {
    const { nrOfStones, index, isActive} = props;
    const { setGameState } = useMancalaGame();
    
        const [alert, setAlert] = useState<string | null>(null);
    
        const clickPit = async () => {
            const result = await playPit(index);
    
            if (isGameState(result)) {
                setGameState(result);
                if (result.players[0].hasTurn) {
                    document.getElementById("currentTurnField").textContent = result.players[0].name;
                    document.getElementById("currentTurnDiv").style = "background-color: rgb(0,0,255); color: white";
                } else {
                    document.getElementById("currentTurnField").textContent = result.players[1].name;
                    document.getElementById("currentTurnDiv").style = "background-color: rgb(255,0,0); color: white";
                }
                if (result.gameStatus.endOfGame) {
                    document.getElementById("winnerNameField").textContent = result.gameStatus.winner + " wins!"
                }
            } else {
                setAlert(`${result.statusCode} ${result.statusText}`);
            }
        }

    return (<button 
        className= {styles.PitButton}
        onClick={() => clickPit()}>
        <span style={{fontSize: '1vw', color:'rgb(238,217,196)'}}>{index}</span><br/>
        {nrOfStones}
    </button>)
}
