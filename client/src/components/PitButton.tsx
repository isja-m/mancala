import classNames from "classnames";
import { playPit} from "../services/api";
import { useMancalaGame } from "../contexts/MancalaGameContext";
import { useState } from "react";
import { startGame } from "../services/api";
import { GameState, isGameState } from "../types";

type Props = {
    nrOfStones: number;
    index: number;
    gameState : GameState;
    isActive : boolean;
};
export const PitButton = (props: Props) => {
    const { nrOfStones, index, gameState, isActive} = props;
    const { setGameState } = useMancalaGame();
    
        const [alert, setAlert] = useState<string | null>(null);

        const resetGame = async (gameState : GameState) => {
            console.log(gameState.gameStatus.winner);
            console.log(gameState);
            const result = await startGame(gameState.players[0].name, gameState.players[1].name);
    
            if (isGameState(result)) {
                setGameState(result);
            } else {
                setAlert(`${result.statusCode} ${result.statusText}`);
            }
        }
    
        const clickPit = async () => {
            const result = await playPit(index);
    
            if (isGameState(result)) {
                setGameState(result);
                if (result.gameStatus.endOfGame) {
                    resetGame(result);
                }
            } else {
                setAlert(`${result.statusCode} ${result.statusText}`);
            }
        }

    return (<button className={classNames(
        "py-1 px-3 rounded-full text-xl border-4",
        "hover:text-neutral-800 ", "hover:bg-neutral-50", "hover:border-neutral-50 duration-300",
        { "text-neutral-300 bg-sogyo border-neutral-300": !isActive },
        { "text-neutral-800 bg-neutral-50 border-neutral-50": isActive })}
        onClick={() => clickPit()}>
        {index}<br/>
        {nrOfStones}
    </button>)
}
