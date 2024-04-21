import React from 'react';

interface InputStringProps {
    className : string;
    id : string;
    placeholder : string;
    onChange : (event : React.ChangeEvent<HTMLInputElement>) => void;
}

const InputString: React.FC<InputStringProps> = ({ className, id, placeholder, onChange }) => {
    return (
        <input
            className={className}
            id={id}
            placeholder={placeholder}
            onChange={onChange}
        />
    );
};

export default InputString;