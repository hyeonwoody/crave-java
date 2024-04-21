import React, {ChangeEvent, useState} from 'react';
import RootAxios, {ListenToSSE, InputState} from "./RootAPI"
import InputString from "../../parts/input/InputString"
import Copyright from "../../parts/copyright/Copyright";
import './Root.css';


const Root : React.FC = () => {

    const [input, setInput] = useState<InputState>({
        inputScope: -1,
        inputAlgorithm: -1,
        inputMethod: -1,
        inputOrigin: '',
        inputDestination: '',
        inputHow: -1,
        inputNumStage: -1,
    });
    const [dropDown, setDropDown] = useState ('');

    const [textareas, setTextareas] = useState<any[]>([]);


    const handleInputChange = (field: keyof InputState) => (
        event: ChangeEvent<HTMLInputElement>
    ) => {
        setIsOpen(false);
        setInput((prevInput : InputState) => ({
            ...prevInput,
            [field]: event.target.value,
        }));
        console.log (field, input[field]);
    };
    const handleOptionClick = (field: keyof InputState, option : number) => (
        event: any
    ) => {
        setIsOpen(false);
        console.log (option)
        setInput((prevInput) => ({
            ...prevInput,
            [field]: option,
        }));
        setDropDown(option.toString());

    };


    const [isOpen, setIsOpen] = useState(false);

    const toggleDropdown = () => {
        setIsOpen(!isOpen);
    };

    const generateOptions = () => {
        const options = [];
        for (let i = 1; i <= 16; i++){
            options.push (
                <a
                    key={i}
                    className="block px-4 py-2 text-gray-800 hover:bg-gray-200"
                    onClick={handleOptionClick('inputNumStage', i)}
                >
                    {i}
                </a>
            )
        }
        return options;
    }
    function SSeCallback (event: any){
        console.log(`Received SSE: ${event.data}`)
        const eventData = JSON.parse(event.data);

        setTextareas ((prevTextareas) =>
            prevTextareas.map((textarea)=>
                textarea.id === eventData.port ? { ...textarea, value: textarea.value + eventData.data+'\n'} : textarea
            )
        )
    }

    const onClickSubmit = (event : React.MouseEvent<HTMLButtonElement>) => {
        event.preventDefault();
        function resultCallback (result : number){
            console.log ("result"+result);
            if (result > 17000){
                console.log ("FFF");
                ListenToSSE(result, SSeCallback);
                addInstaceLog(result);
            }
            if (result < 0){
                //error handling
            }
        }
        RootAxios (input, resultCallback);
    }

    const addInstaceLog = (port : number) => {
        console.log ("instance add");
        // @ts-ignore
        setTextareas([...textareas, { id: port, value: '', origin: input.inputOrigin, destination: input.inputDestination }]);
    }





    return (
        <div>
            <div className="Login">
                <div className="Container h-screen">

                    <div className="rounded-b-3xl justify-center items-center inline-flex">
                        <div className="grid grid-rows-none">

                            <div className="grid grid-cols-7 Radio Scope">
                                <div className="flex items-center">
                                    <input type="radio" id="person" name="options1" className="form-radio text-blue-500" value={0} onChange={handleInputChange('inputScope')}/>
                                    <label htmlFor="Person Only" className="ml-2 text-gray-700">Person Only</label>
                                </div>
                                <ul className="p-5 space-x-10"/>
                                <ul className="p-5 space-x-10"/>
                                <ul className="p-5 space-x-10"/>
                                <ul className="p-5 space-x-10"/>
                                <ul className="p-5 space-x-10"/>
                                <div className="flex items-center">
                                    <input type="radio" id="all" name="options1" className="form-radio text-blue-500" value={1} onChange={handleInputChange('inputScope')}/>
                                    <label htmlFor="All" className="ml-2 text-gray-700">All</label>
                                </div>
                            </div>

                            <div className="grid grid-cols-7 Radio Algorithm">
                                <div className="flex items-center">
                                    <input type="radio" id="BFS" name="options2" className="form-radio text-blue-500"
                                           value={1} onChange={handleInputChange('inputAlgorithm')}/>
                                    <label htmlFor="BFS" className="ml-2 text-gray-700">BFS</label>
                                </div>
                                <ul className="p-5 space-x-10"/>
                                <ul className="p-5 space-x-10"/>
                                <ul className="p-5 space-x-10"/>
                                <ul className="p-5 space-x-10"/>
                                <ul className="p-5 space-x-10"/>
                                <div className="flex items-center">
                                    <input type="radio" id="DFS" name="options2" className="form-radio text-blue-500"
                                           value={0} onChange={handleInputChange('inputAlgorithm')}/>
                                    <label htmlFor="Person Only" className="ml-2 text-gray-700">DFS</label>

                                </div>
                            </div>

                            <div className="grid grid-cols-7 Radio Method">
                                <div className="flex items-center">
                                    <input type="radio" id="Front" name="options3" className="form-radio text-blue-500" value={0} onChange={handleInputChange('inputMethod')}/>
                                    <label className="ml-2 text-gray-700">Front</label>
                                </div>
                                <ul className="p-5 space-x-10"/>
                                <ul className="p-5 space-x-10"/>
                                <div className="flex items-center">
                                    <input type="radio" id="Front & Back" name="options3" className="form-radio text-blue-500" value={1} onChange={handleInputChange('inputMethod')}/>
                                    <label className="ml-2 text-gray-700">Front & Back</label>
                                </div>
                                <ul className="p-5 space-x-10"/>
                                <ul className="p-5 space-x-10"/>
                                <div className="flex items-center">
                                    <input type="radio" id="DFS" name="options3" className="form-radio text-blue-500" value={2} onChange={handleInputChange('inputMethod')}/>
                                    <label className="ml-2 text-gray-700">Back</label>
                                </div>
                            </div>
                            <InputString className={"form-control text-1xl"} id={"input.origin"} placeholder={"Origin"} onChange={handleInputChange('inputOrigin')}/>
                            <ul className="p-5 space-y-10"/>
                            <InputString className={"form-control text-1xl"} id={"input.destination"} placeholder={"Destination"} onChange={handleInputChange('inputDestination')}/>

                            <div className="grid grid-cols-7">
                                <div className="flex items-center">
                                    <input type="radio" id="exact" name="options4" className="form-radio text-blue-500" value={0} onChange={handleInputChange('inputHow')}/>
                                    <label htmlFor="Person Only" className="ml-2 text-gray-700">exact</label>
                                </div>
                                <ul className="p-5 space-y-5"/>
                                <div className="flex items-center">
                                    <input type="radio" id="minimum" name="options4" className="form-radio text-blue-500" value={1} onChange={handleInputChange('inputHow')}/>
                                    <label htmlFor="Person Only" className="ml-2 text-gray-700">minimum</label>
                                </div>
                                <ul className="p-5 space-y-5"/>
                                <div className="flex items-center">
                                    <input type="radio" id="maximum" name="options4" className="form-radio text-blue-500" value={2} onChange={handleInputChange('inputHow')}/>
                                    <label htmlFor="Person Only" className="ml-2 text-gray-700">maximum</label>
                                </div>
                                <ul className="p-5 space-y-5"/>
                                <div className="flex items-center">
                                    <input type="radio" id="none" name="options4" className="form-radio text-blue-500" value={3} onChange={handleInputChange('inputHow')}/>
                                    <label htmlFor="Person Only" className="ml-2 text-gray-700">none</label>
                                </div>
                            </div>

                            {input.inputHow !== 3 &&
                                <div className="relative inline-block text-center">
                                <button
                                    onClick={toggleDropdown}
                                    className="bg-gray-300 text-gray-700 py-2 px-4  rounded inline-flex items-center"
                                >
                                    <span className="mr-1">{dropDown}</span>
                                    <svg
                                        className={`fill-current h-4 w-4 ${isOpen ? 'transform rotate-180' : ''}`}
                                        xmlns="http://www.w3.org/2000/svg"
                                        viewBox="0 0 20 20"
                                    >
                                        <path d="M10 12.59l-6.3-6.3a1 1 0 0 1 1.4-1.42L10 10.77l5.3-5.3a1 1 0 0 1 1.4 1.42L11.4 12.6a1 1 0 0 1-1.4 0z" />
                                    </svg>
                                </button>
                                {isOpen && (
                                    <div className="absolute top-1/2 left-1/2 transform -translate-x-1/2 -translate-y-1/10 w-48 bg-white border rounded-md shadow-lg">
                                        {generateOptions()}

                                    </div>
                                )}
                            </div>
                            }
                            <ul className="p-5 space-y-30"/>
                            <button className="py-2 px-4 rounded-3xl shadow-md text-white bg-blue-500 hover:bg-green-300" type="submit" onClick={onClickSubmit}>submit</button>
                            {textareas.map((textarea)=>(
                                <div key={textarea.id}>
                                    <label htmlFor={`textarea-${textarea.id}`}>{`Textarea ${textarea.id} -${textarea.origin}-${textarea.destination}:`}</label>
                                    <textarea
                                        id={`textarea-${textarea.id}zzzzzz`}
                                        value={textarea.value}

                                        placeholder={`Type here...`}
                                        rows={4}
                                        cols={50}
                                    />
                                </div>
                            ))}
                        </div>

                    </div>

                    <Copyright/>
                </div>
            </div>
        </div>
    );
}

export default Root;