/* tslint:disable */
/* eslint-disable */
/**
 * OpenAPI definition
 * No description provided (generated by Openapi Generator https://github.com/openapitools/openapi-generator)
 *
 * The version of the OpenAPI document: v0
 * 
 *
 * NOTE: This class is auto generated by OpenAPI Generator (https://openapi-generator.tech).
 * https://openapi-generator.tech
 * Do not edit the class manually.
 */

import { mapValues } from '../runtime';
/**
 * 
 * @export
 * @interface Pipeline
 */
export interface Pipeline {
    /**
     * 
     * @type {number}
     * @memberof Pipeline
     */
    id?: number;
    /**
     * 
     * @type {string}
     * @memberof Pipeline
     */
    name: string;
    /**
     * 
     * @type {string}
     * @memberof Pipeline
     */
    description?: string;
    /**
     * 
     * @type {number}
     * @memberof Pipeline
     */
    ownerId: number;
    /**
     * 
     * @type {string}
     * @memberof Pipeline
     */
    transformations: string;
    /**
     * 
     * @type {string}
     * @memberof Pipeline
     */
    initialImage?: string;
}

/**
 * Check if a given object implements the Pipeline interface.
 */
export function instanceOfPipeline(value: object): value is Pipeline {
    if (!('name' in value) || value['name'] === undefined) return false;
    if (!('ownerId' in value) || value['ownerId'] === undefined) return false;
    if (!('transformations' in value) || value['transformations'] === undefined) return false;
    return true;
}

export function PipelineFromJSON(json: any): Pipeline {
    return PipelineFromJSONTyped(json, false);
}

export function PipelineFromJSONTyped(json: any, ignoreDiscriminator: boolean): Pipeline {
    if (json == null) {
        return json;
    }
    return {
        
        'id': json['id'] == null ? undefined : json['id'],
        'name': json['name'],
        'description': json['description'] == null ? undefined : json['description'],
        'ownerId': json['ownerId'],
        'transformations': json['transformations'],
        'initialImage': json['initialImage'] == null ? undefined : json['initialImage'],
    };
}

export function PipelineToJSON(json: any): Pipeline {
    return PipelineToJSONTyped(json, false);
}

export function PipelineToJSONTyped(value?: Pipeline | null, ignoreDiscriminator: boolean = false): any {
    if (value == null) {
        return value;
    }

    return {
        
        'id': value['id'],
        'name': value['name'],
        'description': value['description'],
        'ownerId': value['ownerId'],
        'transformations': value['transformations'],
        'initialImage': value['initialImage'],
    };
}

