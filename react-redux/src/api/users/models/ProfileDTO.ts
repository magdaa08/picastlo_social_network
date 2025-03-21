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
 * @interface ProfileDTO
 */
export interface ProfileDTO {
    /**
     * 
     * @type {number}
     * @memberof ProfileDTO
     */
    id: number;
    /**
     * 
     * @type {number}
     * @memberof ProfileDTO
     */
    userId?: number;
    /**
     * 
     * @type {string}
     * @memberof ProfileDTO
     */
    bio?: string;
    /**
     * 
     * @type {string}
     * @memberof ProfileDTO
     */
    avatar?: string;
}

/**
 * Check if a given object implements the ProfileDTO interface.
 */
export function instanceOfProfileDTO(value: object): value is ProfileDTO {
    if (!('id' in value) || value['id'] === undefined) return false;
    return true;
}

export function ProfileDTOFromJSON(json: any): ProfileDTO {
    return ProfileDTOFromJSONTyped(json, false);
}

export function ProfileDTOFromJSONTyped(json: any, ignoreDiscriminator: boolean): ProfileDTO {
    if (json == null) {
        return json;
    }
    return {
        
        'id': json['id'],
        'userId': json['userId'] == null ? undefined : json['userId'],
        'bio': json['bio'] == null ? undefined : json['bio'],
        'avatar': json['avatar'] == null ? undefined : json['avatar'],
    };
}

export function ProfileDTOToJSON(json: any): ProfileDTO {
    return ProfileDTOToJSONTyped(json, false);
}

export function ProfileDTOToJSONTyped(value?: ProfileDTO | null, ignoreDiscriminator: boolean = false): any {
    if (value == null) {
        return value;
    }

    return {
        
        'id': value['id'],
        'userId': value['userId'],
        'bio': value['bio'],
        'avatar': value['avatar'],
    };
}

