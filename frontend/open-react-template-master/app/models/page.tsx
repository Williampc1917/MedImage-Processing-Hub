'use client';
import { useState, useRef } from "react";
import Link from 'next/link'
import UploadImageClient from "@/components/UploadImage.client";
import {document} from "postcss";

export const metadata = {
    title: 'Pneumonia X-Ray Analysis - Open PRO',
    description: 'Upload an X-ray to get instant pneumonia detection and analysis.',
}



export default function PneumoniaAnalysis() {

    const [uploadedImage, setUploadedImage] = useState<{ file: File | null, url: string | null }>({ file: null, url: null });
    const [analysisResult, setAnalysisResult] = useState<{ diagnosis: string, confidence: number } | null>(null); // Added this state for analysis results
    const [isLoading, setIsLoading] = useState(false);

    const nameRef = useRef(null);
    const ageRef = useRef(null);

    const handleImageUpload = (file) => {
        const url = URL.createObjectURL(file);
        setUploadedImage({ file, url });
    };

    const handleSubmit = async (event) => {
        event.preventDefault();

        const formData = new FormData();
        if (uploadedImage.file) {
            formData.append('image', uploadedImage.file);
        }

        const name = nameRef.current.value;
        const age = ageRef.current.value;

        formData.append('name', name);
        formData.append('age', age);


        setIsLoading(true); // Start loading state
        try {
            const response = await fetch('http://localhost:8080/api/upload', {
                method: 'POST',

                body: formData,
            });
            const data = await response.json();
            console.log(data);
            setAnalysisResult(data);  // Store analysis results in state
        } catch (error) {
            console.error("There was an error uploading the image:", error);
        }
        setIsLoading(false); // End loading state
    };


    return (


        <section className="relative">
            <div className="max-w-6xl mx-auto px-4 sm:px-6">
                <div className="pt-32 pb-12 md:pt-40 md:pb-20">

                    {/* Loading Screen */}
                    {isLoading && (
                        <div className="fixed top-0 left-0 w-full h-full bg-gray-800 bg-opacity-50 z-50 flex items-center justify-center">
                            <div className="bg-white p-6 rounded shadow-md text-black">
                                <p>Loading...</p>
                            </div>
                        </div>
                    )}

                    {/* Page header */}
                    <div className="max-w-3xl mx-auto text-center pb-12 md:pb-20">
                        <h1 className="h1">Pneumonia X-Ray Analysis</h1>
                        <p className="text-gray-400">Upload an X-ray to get instant pneumonia detection and analysis.</p>
                    </div>

                    {/* Upload Section */}
                    <div className="max-w-sm mx-auto mb-6">
                        <div className="flex flex-wrap -mx-3 mb-4">
                            <div className="w-full px-3">
                                <UploadImageClient onUpload={handleImageUpload} />
                                {uploadedImage.url && (
                                    <img src={uploadedImage.url} alt="Uploaded Preview" className="mt-4" />
                                )}
                            </div>
                        </div>
                    </div>

                    {/* Patient Details Form */}
                    <div className="max-w-sm mx-auto">
                        <form onSubmit={handleSubmit}>
                            {/* Name Field */}
                            <div className="w-full px-3 mt-4">
                                <label className="block text-gray-300 text-sm font-medium mb-1" htmlFor="name">Name</label>
                                <input type="text" ref={nameRef} id="name" className="form-input w-full text-gray-300" placeholder="Enter your name..." />
                            </div>

                            {/* Age Field */}
                            <div className="w-full px-3 mt-4">
                                <label className="block text-gray-300 text-sm font-medium mb-1" htmlFor="age">Age</label>
                                <input type="number" ref={ageRef} id="age" className="form-input w-full text-gray-300" placeholder="Enter your age..." />
                            </div>

                            {/* Comment Field */}
                            <div className="w-full px-3 mt-4">
                                <label className="block text-gray-300 text-sm font-medium mb-1" htmlFor="comments">Comments</label>
                                <textarea id="comments" className="form-textarea w-full text-gray-300" placeholder="Any comments..."></textarea>
                            </div>

                            <div className="flex flex-wrap -mx-3 mt-6">
                                <div className="w-full px-3">
                                    <button type="submit" className="btn text-white bg-purple-600 hover:bg-purple-700 w-full">Submit for Analysis</button>
                                </div>
                            </div>
                        </form>
                    </div>

                    {/* Analysis Result Display */}
                    {analysisResult && (
                        <div className="max-w-sm mx-auto mt-6 text-center">
                            <h2 className="text-lg text-gray-300 mb-2">Analysis Result</h2>
                            <p className="text-xl font-bold">{analysisResult.diagnosis}</p>
                            <p className="text-gray-400">{(analysisResult.confidence * 100).toFixed(2)}% confidence</p>
                        </div>
                    )}
                </div>
            </div>
        </section>
    )
}