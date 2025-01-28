import { useMancalaGame } from "../contexts/MancalaGameContext";
import { getSeeds} from "../services/api";
import { PitButton } from "../components/PitButton";


export const Play = () => {
    const { gameState, setGameState } = useMancalaGame();

    return <div>
        Player 1: {gameState?.players[0].name}<br />
        Player 2: {gameState?.players[1].name}<br />
        <table>
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
        <span id="winnerNameField"></span>
    </div>
};