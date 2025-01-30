import classNames from "classnames";
import { useMancalaGame } from "../contexts/MancalaGameContext";
import { useState } from "react";
import { startGame } from "../services/api";
import { GameState, isGameState } from "../types";
import styles from '../Play.module.css';

type Props = {
    isActive : boolean;
};
export const ReplayButton = (props: Props) => {
    const { nrOfStones, index, isActive} = props;
    const { gameState, setGameState } = useMancalaGame();
    
        const [alert, setAlert] = useState<string | null>(null);

        const resetGame = async (gameState : GameState) => {
            const result = await startGame(gameState.players[0].name, gameState.players[1].name);
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
    
        const replay = async () => {
            if (isActive) {
                resetGame(gameState);
            }
        }

    return (<button className={classNames(
        "py-1 px-3 rounded-full text-xl border-4", styles.ReplayButton,
        "hover:text-neutral-800 ", "hover:bg-neutral-50", "hover:border-neutral-50 duration-300",
        { "text-neutral-300 bg-sogyo border-neutral-300": isActive },
        { "text-neutral-800 bg-neutral-50 border-neutral-50": !isActive })}
        onClick={() => replay(gameState)}>
        Replay
    </button>)
}
