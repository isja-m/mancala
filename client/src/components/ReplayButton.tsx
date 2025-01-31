import classNames from "classnames";
import { useMancalaGame } from "../contexts/MancalaGameContext";
import { useState } from "react";
import { startGame } from "../services/api";
import { GameState, isGameState } from "../types";
import styles from '../Play.module.css';

type Props = {
    isActive : boolean|undefined;
};
export const ReplayButton = (props: Props) => {
    const { isActive} = props;
    const { gameState, setGameState } = useMancalaGame();
    
        const [alert, setAlert] = useState<string | null>(null);

        const resetGame = async (gameState : GameState | undefined) => {
            const result = await startGame(gameState?.players[0].name, gameState?.players[1].name);
            document.getElementById("PlayerOneField").style = "";
            document.getElementById("PlayerTwoField").style = "opacity: 0.5;";
            document.getElementById("winnerOneField").style = "display: none;"
            document.getElementById("winnerTwoField").style = "display: none;"
            document.getElementById("drawField").style = "display: none;"
    
            if (isGameState(result)) {
                setGameState(result);
            } else {
                setAlert(`${result.statusCode} ${result.statusText}`);
            }
        }
    
        const replay = async (gameState: GameState | undefined) => {
            if (isActive) {
                resetGame(gameState);
            }
        }

    return (<button
        className={classNames(styles.ReplayButton, (isActive ? styles.ReplayButtonActive : styles.ReplayButtonInactive))}
        onClick={() => replay(gameState)}>
        Replay
    </button>)
}
