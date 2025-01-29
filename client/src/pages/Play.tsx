import { useMancalaGame } from "../contexts/MancalaGameContext";
import { getSeeds} from "../services/api";
import { PitButton } from "../components/PitButton";
import { ReplayButton } from "../components/ReplayButton";
import styles from '../Play.module.css';


export const Play = () => {
    const { gameState, setGameState } = useMancalaGame();

    return <div>
        <table>
            <tr>
                <td>Player 1: {gameState?.players[0].name}</td><td>&emsp;</td><td><div style={{backgroundColor:'rgb(0,0,255)'}}>&emsp;&emsp;</div></td>
            </tr>
            <tr>
                <td>Player 2: {gameState?.players[1].name}</td><td>&emsp;</td><td><div style={{backgroundColor:'rgb(255,0,0)'}}>&emsp;&emsp;</div></td>
            </tr>
        </table>
        <table><tr>
            <td><div style={{backgroundColor:'rgb(0,0,255)', color: 'white'}} id="currentTurnDiv">
                Current turn: <span id="currentTurnField">{gameState?.players[0].name}</span>&emsp;
            </div></td>
        </tr></table>

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
        
        &emsp;<span id="winnerNameField"></span> <br/>
        <ReplayButton isActive={gameState.gameStatus.endOfGame}/>
    </div>
};