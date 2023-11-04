import React, { useState } from 'react';

const UploadImageClient = ({ onUpload }: { onUpload: (file: File) => void }) => {
    const [uploadedImage, setUploadedImage] = useState<File | null>(null);

    const handleImageUpload = (e: React.ChangeEvent<HTMLInputElement>) => {
        const file = e.target.files && e.target.files[0];
        if (file) {
            setUploadedImage(file);
            onUpload(file);  // Pass the file back to the parent
        }
    };

    return (
        <div className="w-full px-3">
            <label htmlFor="upload-button" className="btn text-white bg-purple-600 hover:bg-purple-700 w-full cursor-pointer">
                Upload Image (.jpg, .png, .dicom)
            </label>
            <input
                id="upload-button"
                type="file"
                accept=".jpg, .png, .dicom"
                onChange={handleImageUpload}
                style={{ display: 'none' }}
            />
            {uploadedImage && (
                <div className="w-full px-3 mt-4">
                    <p>{uploadedImage.name}</p>
                </div>
            )}
        </div>
    );
}

export default UploadImageClient;