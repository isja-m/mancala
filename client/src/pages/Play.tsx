import { useMancalaGame } from "../contexts/MancalaGameContext";
import { getSeeds} from "../services/api";
import { PitButton } from "../components/PitButton";
import { ReplayButton } from "../components/ReplayButton";
import classNames from "classnames";
import styles from '../Play.module.css';


export const Play = () => {
    const { gameState, setGameState } = useMancalaGame();
    var playersTurn = "";
    var turnColor = "";
    var playerOneOpacity = 1;
    var playerTwoOpacity = 1;
    if (gameState.players[0].hasTurn) {
        playerTwoOpacity = 0.5;
    } else {
        playerOneOpacity = 0.5;
    }

    return <div>
        <table className= {styles.PlayerTable}>
            <tr>
                <td><div id="PlayerOneField" style={{opacity: playerOneOpacity}} className={classNames(styles.PlayerOne, styles.PlayerField)}>Player 1: {gameState?.players[0].name}</div></td>
                <td><div id="PlayerTwoField" style={{opacity: playerTwoOpacity}} className={classNames(styles.PlayerTwo, styles.PlayerField)}>Player 2: {gameState?.players[1].name}</div></td>
            </tr>
        </table>

        <table className= {styles.MancalaBoard}>
            <tbody>
                <tr>
                    <td></td>
                    <td><PitButton nrOfStones={getSeeds(0, gameState)} index={0}/></td>
                    <td><PitButton nrOfStones={getSeeds(1, gameState)} index={1}/></td>
                    <td><PitButton nrOfStones={getSeeds(2, gameState)} index={2}/></td>
                    <td><PitButton nrOfStones={getSeeds(3, gameState)} index={3}/></td>
                    <td><PitButton nrOfStones={getSeeds(4, gameState)} index={4}/></td>
                    <td><PitButton nrOfStones={getSeeds(5, gameState)} index={5}/></td>
                </tr>
                <tr>
                    <td><PitButton nrOfStones={getSeeds(13, gameState)} index={13}/></td>
                    <td></td>
                    <td></td>
                    <td></td>
                    <td></td>
                    <td></td>
                    <td></td>
                    <td><PitButton nrOfStones={getSeeds(6, gameState)} index={6}/></td>
                </tr>
                <tr>
                    <td></td>
                    <td><PitButton nrOfStones={getSeeds(12, gameState)} index={12}/></td>
                    <td><PitButton nrOfStones={getSeeds(11, gameState)} index={11}/></td>
                    <td><PitButton nrOfStones={getSeeds(10, gameState)} index={10}/></td>
                    <td><PitButton nrOfStones={getSeeds(9, gameState)} index={9}/></td>
                    <td><PitButton nrOfStones={getSeeds(8, gameState)} index={8}/></td>
                    <td><PitButton nrOfStones={getSeeds(7, gameState)} index={7}/></td>
                </tr>
            </tbody>
        </table>
        
        <td><ReplayButton isActive={gameState.gameStatus.endOfGame}/></td>
        <td><div style={{display: "none"}} className={classNames(styles.PlayerField, styles.PlayerOne, styles.WinnerName)} id="winnerOneField">{gameState?.players[0].name} wins!</div></td>
        <td><div style={{display: "none"}} className={classNames(styles.PlayerField, styles.PlayerTwo, styles.WinnerName)} id="winnerTwoField">{gameState?.players[1].name} wins!</div></td>
        <td><div style={{display: "none"}} className={classNames(styles.PlayerField, styles.Draw, styles.WinnerName)} id="drawField">Draw!</div></td>
    </div>
};