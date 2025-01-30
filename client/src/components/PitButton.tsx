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
                    document.getElementById("PlayerOneField").style = "";
                    document.getElementById("PlayerTwoField").style = "opacity: 0.5;";
                } else {
                    document.getElementById("PlayerOneField").style = "opacity: 0.5;";
                    document.getElementById("PlayerTwoField").style = "";
                }
                if (result.gameStatus.endOfGame && result.gameStatus.winner == result.players[0].name) {
                    document.getElementById("winnerOneField").style = "display: default;"
                } else if (result.gameStatus.endOfGame && result.gameStatus.winner == result.players[1].name) {
                    document.getElementById("winnerTwoField").style = "display: default;"
                } else if (result.gameStatus.endOfGame) {
                    document.getElementById("drawField").style = "display: default;"
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
