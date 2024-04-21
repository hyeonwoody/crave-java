import axios from 'axios';
import {My} from '../../configuration/web/webConfig';

export interface InputState {
    inputScope: number;
    inputAlgorithm: number;
    inputMethod: number;
    inputOrigin: string;
    inputDestination: string;
    inputHow: number;
    inputNumStage: number;
}
const my = new My();

export const ListenToSSE = (port : number, SSeCallback: (data: any) => void) =>{
    const eventSource = new EventSource(`http://${my.ipAddress}:${my.backEndPort}/textarea/created?port=${port}`);

    eventSource.onmessage = (event) =>{
        console.log("AAACACA");
        SSeCallback(event);

    };

    return () =>{
        eventSource.close();
    }
}
export function RootAxios(input : InputState ,resultCallback: (data: any) => void) {

    axios({
        url: "config/" + "main",
        method: 'put',
        baseURL: `http://${my.ipAddress}:${my.backEndPort}`,
        withCredentials: true,
        data: input
    }).then(function (response) {
        resultCallback(response.data);
    });


};




export default RootAxios;