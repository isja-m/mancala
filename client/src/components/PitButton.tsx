import classNames from "classnames";
import { playPit } from "../services/api";

type Props = {
    nrOfStones: number;
    index: number;
    isActive: boolean;
};
export const PitButton = (props: Props) => {
    const { nrOfStones, index, isActive } = props;

    return (<button className={classNames(
        "py-1 px-3 rounded-full text-xl border-4",
        "hover:text-neutral-800 ", "hover:bg-neutral-50", "hover:border-neutral-50 duration-300",
        { "text-neutral-300 bg-sogyo border-neutral-300": !isActive },
        { "text-neutral-800 bg-neutral-50 border-neutral-50": isActive })}
        onClick={() => playPit(index)}>
        {nrOfStones}
    </button>)
}
